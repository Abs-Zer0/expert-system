<%-- any content can be specified here e.g.: --%>
<%@ page pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<style>
    #field tr td input {
        min-width: 8vw;
    }
</style>

<div class="row m-0 pb-2 text-center align-items-stretch">
    <div class="col-2">
        <a class="btn btn-primary w-100" href="/">Назад</a>
    </div>
    <div class="col-4">
        <p class="text-primary">Установить размер поля</p>
        <div class="form-group row m-0">
            <input class="form-control col-5" id="field_width" type="number" min="1" step="1" placeholder="width"/>
            <p class="font-weight-bold col-2">X</p>
            <input class="form-control col-5" id="field_height" type="number" min="1" step="1" placeholder="height"/>
        </div>
    </div>
    <div class="col-2 ">
        <button class="btn btn-primary w-100" onclick="send_data()">Получить результат</button>
    </div>
</div>

<div class="row m-0 text-center align-items-baseline">
    <div class="col-3 row" id="figure-list">
        <div class="col-6 overflow-auto">
            <h5 class="text-success font-weight-bold">Свои фигуры</h5>
            <c:forEach items="${figures}" var="figure">
                <input class="form-control" value="${figure.getName()}" readonly="true"/>
            </c:forEach>
        </div>
        <div class="col-6 overflow-auto">
            <h5 class="text-danger font-weight-bold">Вражеские фигуры</h5>
            <c:forEach items="${figures}" var="figure">
                <input class="form-control" value="${figure.getName()} [E]" readonly="true"/>
            </c:forEach>
        </div>
    </div>
    <div class="col-9 row align-items-baseline">
        <div class="col-10 overflow-auto">
            <table id="field">

            </table>
        </div>
        <div class="col-2 overflow-auto d-none" id="output">
            <div class="alert alert-primary"></div>
            <button class="btn btn-secondary" data-toggle="collapse" data-target="#output-trace" aria-expanded="false" aria-controls="output-trace">
                Пояснение решения
            </button>
            <div class="collapse" id="output-trace">
                <div class="card card-body text-left"></div>
            </div>
        </div>
    </div>
</div>

<div class="fixed-bottom d-flex flex-column-reverse " id="alert-list"></div>

<script>
    let table = '#field';

    $(document).ready(function () {
        $('#field_width').change(sizeChanged);
        $('#field_height').change(sizeChanged);

        $('#field_width').val(8);
        $('#field_height').val(8);
        sizeChanged();
    });

    function sizeChanged() {
        let new_width = $('#field_width').val();
        let new_height = $('#field_height').val();

        let cur_width = table_cols(table);
        let cur_height = table_rows(table);

        if (new_width > cur_width) {
            for (var i = 0; i < new_width - cur_width; i++) {
                add_col();
            }
        }
        if (new_width < cur_width) {
            for (var i = 0; i < cur_width - new_width; i++) {
                remove_col();
            }
        }

        if (new_height > cur_height) {
            for (var i = 0; i < new_height - cur_height; i++) {
                add_row(new_width);
            }
        }
        if (new_height < cur_height) {
            for (var i = 0; i < cur_height - new_height; i++) {
                remove_row();
            }
        }
    }

    function table_rows() {
        return $(table).find('tr').length;
    }

    function table_cols() {
        return $(table).find('tr td').length / $(table).find('tr').length;
    }

    function add_row(cells) {
        $(table).append(row_template(cells));
    }

    function add_col() {
        $(table).find('tr').each((i, e) => $(e).append(cell_template()));
    }

    function remove_row() {
        $(table).find('tr:last-child').remove();
    }

    function remove_col() {
        $(table).find('tr').each((i, e) => $(e).find('td:last-child').remove());
    }

    function row_template(cells) {
        var res = "<tr>";
        for (var i = 0; i < cells; i++)
            res += cell_template();

        return res + "</tr>"
    }

    function cell_template() {
        return "<td><input class='form-control'/></td>";
    }
</script>
<script>
    let output = '#output';
    let output_trace = '#output-trace';

    function send_data() {
        let field = new Array();
        for (var i = 0; i < table_rows(); i++) {
            field.push(new Array());
            for (var j = 0; j < table_cols(); j++) {
                field[i][j] = $(table)
                        .find('tr:nth-child(' + (i + 1) + ') td:nth-child(' + (j + 1) + ') input')
                        .val();
            }
        }

        let xhr = $.ajax({
            method: 'POST',
            url: '/user/data',
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            data: JSON.stringify(field.reverse())
        }).done(function (res) {
            if ($(output).hasClass('d-none'))
                $(output).removeClass('d-none');

            let solution = $.makeArray(res.solutions)[0];
            $(output).find('.alert.alert-primary').first().html(solution.result);

            $(output_trace).find('.card.card-body').first().html("");
            $.each(solution.trace, function (i, e) {
                $(output_trace).find('.card.card-body').first().append(trace_part_template(e));
            });
        }).error(function () {
            console.log('some error');
        }).complete(function () {
            console.log('complete');
        });
    }

    function trace_part_template(msg) {
        return '<p>' + msg + '</p>';
    }
</script>
<script>
    let alert_list = '#alert-list';
    let figure_list = '#figure-list';

    $(document).ready(function () {
        $(figure_list).find('input').each((i, e) => $(e).click(function (event) {
                event.preventDefault();

                $(this).select();
                document.execCommand('copy');

                add_alert_info('Copied to clipboard');
            }));
    });

    function add_alert_info(msg) {
        if ($(alert_list).find('.alert').length > 2)
            $(alert_list).find('.alert').first().remove();

        let alert = $(alert_info(msg)).delay(2000).fadeOut(1000, function () {
            $(this).remove();
        });
        $(alert_list).append(alert);
    }

    function alert_info(msg) {
        return '<div class="alert alert-info">' + msg + '</div>';
    }
</script>