package platform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Arrays;
import java.util.UUID;

@Controller
public class HtmlController {

    @Autowired
    SnippetsService snippetsService;

    @Autowired
    ApiController apiController;

    public HtmlController(){}

    @GetMapping(path = "/code/new")
    public String getNewCode() {
        return "newCode";
    }

    @GetMapping(path = "/code/latest")
    public String getLatestCode(Model model) {
        model.addAttribute("codeList", Arrays.asList(snippetsService.getLatestCode()));
        return "latestCode";
    }

    @GetMapping(value = "/code/{snippetId}")
    public String getCodeHTML(Model model, @PathVariable(value = "snippetId") String snippetId) {
        UUID uuid = UUID.fromString(snippetId);
        Code code = snippetsService.getCodeFromRepository(uuid);
        if (code.isTimeLimit()) {
            snippetsService.updateTimeById(uuid);
        }
        if (code.isViewsLimit()) {
            snippetsService.updateViewsById(uuid);
        }
        Code updatedCode = snippetsService.getCodeFromRepository(uuid);
        if (!code.isTimeLimit() && !code.isViewsLimit()) {
            model.addAttribute("date", updatedCode.getDate());
            model.addAttribute("code", updatedCode.getCode());
            return "getCode";
        } else {
            model.addAttribute("code", updatedCode.getCode());
            model.addAttribute("date", updatedCode.getDate());
            model.addAttribute("time", updatedCode.getTime());
            model.addAttribute("views", updatedCode.getViews());
            return "getCodeByUuid";
        }
    }

}
