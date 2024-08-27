package servlet;

import entity.TournamentParticipant;
import entity.User;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import listener.ContextListener;
import service.home.TournamentHelperService;

import java.io.IOException;
import java.util.Comparator;
import java.util.Optional;

@WebServlet(Route.HOME)
public class IndexServlet extends HttpServlet {
    private static final String USER_DTO = "userDto";
    private TournamentHelperService tournamentHelperService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        var servletContext = config.getServletContext();
        tournamentHelperService = (TournamentHelperService) servletContext.getAttribute(ContextListener.TOURNAMENT_HELPER_SERVICE);
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var optionalTournament = tournamentHelperService.getNewTournament();
        optionalTournament.ifPresent(tournament -> tournament.getParticipants()
                .sort(Comparator.comparing(
                        (TournamentParticipant tp) -> tp.getUser().getRating()
                ).reversed())
        );
        tournamentHelperService.setSessionAttributes(req,resp, optionalTournament);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var optionalTournament = tournamentHelperService.getNewTournament();
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(USER_DTO);
        if (optionalTournament.isPresent()) {
            tournamentHelperService.participate(user, optionalTournament.get());
            resp.sendRedirect(Route.HOME);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var optionalTournament = tournamentHelperService.getNewTournament();
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(USER_DTO);
        if (optionalTournament.isPresent()) {
            Optional<TournamentParticipant> tournamentParticipant = optionalTournament.get().getParticipants().stream()
                    .filter(participant -> participant.getUser().getId().equals(user.getId()))
                    .findFirst();
            if (tournamentParticipant.isPresent()) {
                System.out.println(tournamentParticipant.get().getId());
                tournamentHelperService.removeFromTournament(tournamentParticipant.get().getId());
                resp.sendRedirect(Route.HOME);
            }
        }
    }
}
