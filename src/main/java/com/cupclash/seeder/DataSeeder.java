package com.cupclash.seeder;

import com.cupclash.model.Match;
import com.cupclash.model.Team;
import com.cupclash.repository.MatchRepository;
import com.cupclash.repository.TeamRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class DataSeeder implements CommandLineRunner {

    private final TeamRepository teamRepository;
    private final MatchRepository matchRepository;

    public DataSeeder(TeamRepository teamRepository, MatchRepository matchRepository) {
        this.teamRepository = teamRepository;
        this.matchRepository = matchRepository;
    }

    @Override
    public void run(String... args) {
        if (teamRepository.count() > 0) return;
        seedTeams();
        seedGroupStageMatches();
    }

    private void seedTeams() {
        // Official 2026 FIFA World Cup teams — Elo ratings from worldfootballelo.com
        // isHost=true triggers the +100 Elo boost in EloService

        // ── Group A: Mexico (host), South Africa, South Korea, Czechia ──────────
        save("Mexico",                 1836, "CONCACAF", true);
        save("South Africa",           1715, "CAF",      false);
        save("South Korea",            1872, "AFC",      false);
        save("Czechia",                1825, "UEFA",     false);

        // ── Group B: Canada (host), Bosnia and Herzegovina, Qatar, Switzerland ───
        save("Canada",                 1720, "CONCACAF", true);
        save("Bosnia and Herzegovina", 1782, "UEFA",     false);
        save("Qatar",                  1620, "AFC",      false);
        save("Switzerland",            1889, "UEFA",     false);

        // ── Group C: Brazil, Morocco, Haiti, Scotland ─────────────────────────────
        save("Brazil",                 2044, "CONMEBOL", false);
        save("Morocco",                1928, "CAF",      false);
        save("Haiti",                  1578, "CONCACAF", false);
        save("Scotland",               1852, "UEFA",     false);

        // ── Group D: USA (host), Paraguay, Australia, Turkey ─────────────────────
        save("USA",                    1855, "CONCACAF", true);
        save("Paraguay",               1762, "CONMEBOL", false);
        save("Australia",              1815, "AFC",      false);
        save("Turkey",                 1893, "UEFA",     false);

        // ── Group E: Germany, Curacao, Ivory Coast, Ecuador ──────────────────────
        save("Germany",                2031, "UEFA",     false);
        save("Curacao",                1538, "CONCACAF", false);
        save("Ivory Coast",            1752, "CAF",      false);
        save("Ecuador",                1792, "CONMEBOL", false);

        // ── Group F: Netherlands, Japan, Sweden, Tunisia ──────────────────────────
        save("Netherlands",            1992, "UEFA",     false);
        save("Japan",                  1932, "AFC",      false);
        save("Sweden",                 1873, "UEFA",     false);
        save("Tunisia",                1742, "CAF",      false);

        // ── Group G: Belgium, Egypt, Iran, New Zealand ────────────────────────────
        save("Belgium",                2001, "UEFA",     false);
        save("Egypt",                  1788, "CAF",      false);
        save("Iran",                   1862, "AFC",      false);
        save("New Zealand",            1578, "OFC",      false);

        // ── Group H: Spain, Cape Verde, Saudi Arabia, Uruguay ────────────────────
        save("Spain",                  2052, "UEFA",     false);
        save("Cape Verde",             1682, "CAF",      false);
        save("Saudi Arabia",           1781, "AFC",      false);
        save("Uruguay",                1896, "CONMEBOL", false);

        // ── Group I: France, Senegal, Iraq, Norway ────────────────────────────────
        save("France",                 2090, "UEFA",     false);
        save("Senegal",                1821, "CAF",      false);
        save("Iraq",                   1728, "AFC",      false);
        save("Norway",                 1821, "UEFA",     false);

        // ── Group J: Argentina, Algeria, Austria, Jordan ──────────────────────────
        save("Argentina",              2134, "CONMEBOL", false);
        save("Algeria",                1748, "CAF",      false);
        save("Austria",                1881, "UEFA",     false);
        save("Jordan",                 1698, "AFC",      false);

        // ── Group K: Portugal, DR Congo, Uzbekistan, Colombia ────────────────────
        save("Portugal",               2010, "UEFA",     false);
        save("DR Congo",               1722, "CAF",      false);
        save("Uzbekistan",             1678, "AFC",      false);
        save("Colombia",               1951, "CONMEBOL", false);

        // ── Group L: England, Croatia, Ghana, Panama ──────────────────────────────
        save("England",                2055, "UEFA",     false);
        save("Croatia",                1921, "UEFA",     false);
        save("Ghana",                  1682, "CAF",      false);
        save("Panama",                 1651, "CONCACAF", false);
    }

    private void seedGroupStageMatches() {
        // Load all teams into a name → Team map for easy lookup
        Map<String, Team> t = teamRepository.findAll().stream()
                .collect(Collectors.toMap(Team::getName, Function.identity()));

        // 12 groups × 6 round-robin matches = 72 group stage matches
        seedGroup("A", t, "Mexico",      "South Africa", "South Korea",            "Czechia");
        seedGroup("B", t, "Canada",      "Bosnia and Herzegovina", "Qatar",         "Switzerland");
        seedGroup("C", t, "Brazil",      "Morocco",      "Haiti",                  "Scotland");
        seedGroup("D", t, "USA",         "Paraguay",     "Australia",              "Turkey");
        seedGroup("E", t, "Germany",     "Curacao",      "Ivory Coast",            "Ecuador");
        seedGroup("F", t, "Netherlands", "Japan",        "Sweden",                 "Tunisia");
        seedGroup("G", t, "Belgium",     "Egypt",        "Iran",                   "New Zealand");
        seedGroup("H", t, "Spain",       "Cape Verde",   "Saudi Arabia",           "Uruguay");
        seedGroup("I", t, "France",      "Senegal",      "Iraq",                   "Norway");
        seedGroup("J", t, "Argentina",   "Algeria",      "Austria",                "Jordan");
        seedGroup("K", t, "Portugal",    "DR Congo",     "Uzbekistan",             "Colombia");
        seedGroup("L", t, "England",     "Croatia",      "Ghana",                  "Panama");
    }

    // Generates all 6 round-robin matches for a group of 4 teams
    private void seedGroup(String group, Map<String, Team> t,
                           String n1, String n2, String n3, String n4) {
        Team t1 = t.get(n1);
        Team t2 = t.get(n2);
        Team t3 = t.get(n3);
        Team t4 = t.get(n4);

        matchRepository.save(new Match(t1, t2, "GROUP", group, null));
        matchRepository.save(new Match(t1, t3, "GROUP", group, null));
        matchRepository.save(new Match(t1, t4, "GROUP", group, null));
        matchRepository.save(new Match(t2, t3, "GROUP", group, null));
        matchRepository.save(new Match(t2, t4, "GROUP", group, null));
        matchRepository.save(new Match(t3, t4, "GROUP", group, null));
    }

    private void save(String name, int elo, String confederation, boolean isHost) {
        teamRepository.save(new Team(name, elo, confederation, isHost));
    }
}
