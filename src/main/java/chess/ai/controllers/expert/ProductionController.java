package chess.ai.controllers.expert;

import chess.ai.controllers.BaseController;
import chess.ai.models.kb.AutocompleteFacts;
import chess.ai.models.kb.Fact;
import chess.ai.models.kb.Production;
import chess.ai.services.kb.FactService;
import chess.ai.services.kb.ProductionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/expert/productions/**")
public class ProductionController extends BaseController {

    @Autowired
    private ProductionService productions;
    @Autowired
    private FactService facts;

    @GetMapping("/")
    public ModelAndView index(Model model) {
        model.addAttribute("productions", productions.getAll());

        return OK(model, "expert/productions");
    }

    @GetMapping("add")
    public ModelAndView addView(Model model) {
        model.addAttribute("production", new Production());
        model.addAttribute("command", "add");
        model.addAttribute("commandMsg", "Добавить");
        model.addAttribute("facts", facts.getAll());

        return OK(model, "expert/productions_edit");
    }

    @PostMapping("add")
    public ModelAndView add(Model model, @ModelAttribute("production") @Valid Production production, BindingResult result, RedirectAttributes attrs) {
        if (result.hasErrors()) {
            model.addAttribute("command", "add");
            model.addAttribute("commandMsg", "Добавить");

            return OK(model, "expert/productions_edit");
        }

        if (productions.isSameExist(production)) {
            attrs.addFlashAttribute("isError", true);
            attrs.addFlashAttribute("errorMsg", "Такая продукция уже есть");

            return Redirect("add");
        }

        productions.addOrUpdate(production);

        attrs.addFlashAttribute("isSuccess", true);
        attrs.addFlashAttribute("successMsg", "Продукция успешно добавлена");

        return Redirect("/expert/productions");
    }

    @GetMapping("/expert/productions/edit/{id}")
    public ModelAndView editView(Model model, @PathVariable long id, RedirectAttributes attrs) {
        try {
            model.addAttribute("production", productions.get(id));
            model.addAttribute("command", "edit");
            model.addAttribute("commandMsg", "Изменить");
            model.addAttribute("facts", facts.getAll());

            return OK(model, "expert/productions_edit");
        } catch (Exception ex) {
            attrs.addFlashAttribute("isError", true);
            attrs.addFlashAttribute("errorMsg", ex.getMessage());

            return Redirect("/expert/productions");
        }
    }

    @PostMapping("edit")
    public ModelAndView edit(Model model, @ModelAttribute("production") @Valid Production production, BindingResult result, RedirectAttributes attrs) {
        if (result.hasErrors()) {
            model.addAttribute("command", "edit");
            model.addAttribute("commandMsg", "Изменить");

            return OK(model, "expert/productions_edit");
        }

        if (productions.isSameExistExcludeId(production, production.getId())) {
            attrs.addFlashAttribute("isError", true);
            attrs.addFlashAttribute("errorMsg", "Такая продукция уже есть");

            return Redirect("add");
        }

        productions.addOrUpdate(production);

        attrs.addFlashAttribute("isSuccess", true);
        attrs.addFlashAttribute("successMsg", "Продукция успешно изменена");

        return Redirect("/expert/productions");
    }

    @GetMapping("/expert/productions/remove/{id}")
    public ModelAndView remove(Model model, @PathVariable long id, RedirectAttributes attrs) {
        try {
            productions.remove(id);

            attrs.addFlashAttribute("isSuccess", true);
            attrs.addFlashAttribute("successMsg", "Продукция успешно удалена");
        } catch (Exception ex) {
            attrs.addFlashAttribute("isError", true);
            attrs.addFlashAttribute("errorMsg", ex.getMessage());
        }

        return Redirect("/expert/productions");
    }

    @GetMapping("facts/filtered")
    public AutocompleteFacts factForAutocomplete(@RequestParam String tmp) {
        final List<Fact> filtered = facts.getFactsWithNameLike(tmp);

        return new AutocompleteFacts(filtered);
    }
}
