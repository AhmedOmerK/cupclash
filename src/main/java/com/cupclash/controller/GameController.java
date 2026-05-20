package com.cupclash.controller;

import com.cupclash.model.Match;
import com.cupclash.repository.MatchRepository;
import com.cupclash.service.EloService;
import com.cupclash.service.EloService.MatchProbability;
import com.cupclash.service.PredictionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/game")
public class GameController {

    private final MatchRepository matchRepository;
    private final EloService eloService;
    private final PredictionService predictionService;

    public GameController(MatchRepository matchRepository,
                          EloService eloService,
                          PredictionService predictionService) {
        this.matchRepository = matchRepository;
        this.eloService = eloService;
        this.predictionService = predictionService;
    }

    // List all group stage matches, organised by group (A → L)
    @GetMapping
    public String showMatchList(Model model) {
        List<Match> matches = matchRepository.findByStage("GROUP");

        Map<String, List<Match>> byGroup = matches.stream()
                .collect(Collectors.groupingBy(
                        Match::getGroupName,
                        LinkedHashMap::new,
                        Collectors.toList()));

        model.addAttribute("matchesByGroup", byGroup);
        return "game";
    }

    // Show a single match with Elo probabilities and the prediction form
    @GetMapping("/{matchId}")
    public String showMatchDetail(@PathVariable long matchId, Model model) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new IllegalArgumentException("Match not found: " + matchId));

        MatchProbability prob = eloService.calculate(match.getTeamA(), match.getTeamB());

        model.addAttribute("match", match);
        model.addAttribute("prob", prob);
        model.addAttribute("alreadyPredicted", predictionService.hasPrediction(matchId));
        return "game-detail";
    }

    // Accept the prediction form and redirect to the result page
    @PostMapping("/predict")
    public String submitPrediction(@RequestParam long matchId,
                                   @RequestParam String predictedOutcome) {
        var saved = predictionService.savePrediction(matchId, predictedOutcome);
        return "redirect:/game/result/" + saved.getId();
    }

    // Show the saved prediction confirmation
    @GetMapping("/result/{predictionId}")
    public String showResult(@PathVariable Long predictionId, Model model) {
        var prediction = predictionService.getById(predictionId);
        MatchProbability prob = eloService.calculate(
                prediction.getMatch().getTeamA(),
                prediction.getMatch().getTeamB());

        model.addAttribute("prediction", prediction);
        model.addAttribute("prob", prob);
        return "result";
    }
}
