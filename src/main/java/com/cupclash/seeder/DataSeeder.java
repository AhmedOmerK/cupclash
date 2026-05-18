package com.cupclash.seeder;

import com.cupclash.model.Team;
import com.cupclash.repository.TeamRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final TeamRepository teamRepository;

    public DataSeeder(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public void run(String... args) {
        if (teamRepository.count() > 0) return; // skip if already seeded
        seedTeams();
    }

    private void seedTeams() {
        // Elo ratings from World Football Elo Ratings (worldfootballelo.com), early 2026
        // isHost=true applies +100 Elo boost in EloService for any home match

        // ── CONMEBOL (6 spots) ────────────────────────────────────────────────
        save("Argentina",   2134, "CONMEBOL", false);
        save("Brazil",      2044, "CONMEBOL", false);
        save("Colombia",    1951, "CONMEBOL", false);
        save("Uruguay",     1896, "CONMEBOL", false);
        save("Ecuador",     1792, "CONMEBOL", false);
        save("Venezuela",   1718, "CONMEBOL", false);

        // ── CONCACAF (6 spots — USA, Mexico, Canada qualify automatically as hosts) ──
        save("USA",         1855, "CONCACAF", true);
        save("Mexico",      1836, "CONCACAF", true);
        save("Canada",      1720, "CONCACAF", true);
        save("Panama",      1651, "CONCACAF", false);
        save("Costa Rica",  1638, "CONCACAF", false);
        save("Honduras",    1558, "CONCACAF", false);

        // ── UEFA (16 spots) ───────────────────────────────────────────────────
        save("France",      2090, "UEFA", false);
        save("England",     2055, "UEFA", false);
        save("Spain",       2052, "UEFA", false);
        save("Germany",     2031, "UEFA", false);
        save("Portugal",    2010, "UEFA", false);
        save("Netherlands", 1992, "UEFA", false);
        save("Italy",       1981, "UEFA", false);
        save("Denmark",     1912, "UEFA", false);
        save("Turkey",      1893, "UEFA", false);
        save("Switzerland", 1889, "UEFA", false);
        save("Austria",     1881, "UEFA", false);
        save("Serbia",      1875, "UEFA", false);
        save("Scotland",    1852, "UEFA", false);
        save("Ukraine",     1843, "UEFA", false);
        save("Hungary",     1812, "UEFA", false);
        save("Albania",     1748, "UEFA", false);

        // ── AFC (8 spots) ─────────────────────────────────────────────────────
        save("Japan",       1932, "AFC", false);
        save("Iran",        1862, "AFC", false);
        save("South Korea", 1872, "AFC", false);
        save("Australia",   1815, "AFC", false);
        save("Saudi Arabia",1781, "AFC", false);
        save("Iraq",        1728, "AFC", false);
        save("Jordan",      1698, "AFC", false);
        save("Uzbekistan",  1678, "AFC", false);

        // ── CAF (9 spots) ─────────────────────────────────────────────────────
        save("Morocco",     1928, "CAF", false);
        save("Senegal",     1821, "CAF", false);
        save("Egypt",       1788, "CAF", false);
        save("Nigeria",     1758, "CAF", false);
        save("Ivory Coast", 1752, "CAF", false);
        save("Cameroon",    1732, "CAF", false);
        save("DR Congo",    1722, "CAF", false);
        save("South Africa",1715, "CAF", false);
        save("Ghana",       1682, "CAF", false);

        // ── OFC (1 spot) ──────────────────────────────────────────────────────
        save("New Zealand", 1578, "OFC", false);

        // ── Intercontinental playoff winners (2 spots) ────────────────────────
        save("Indonesia",   1612, "AFC", false);
        save("Paraguay",    1762, "CONMEBOL", false);
    }

    private void save(String name, int elo, String confederation, boolean isHost) {
        teamRepository.save(new Team(name, elo, confederation, isHost));
    }
}
