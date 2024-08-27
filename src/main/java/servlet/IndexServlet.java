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

@WebServlet(Route.HOME_PAGE)
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
        tournamentHelperService.setSessionAttributes(req, optionalTournament);
        req.getRequestDispatcher(req.getContextPath() + Route.HOME_PAGE_JSP).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var optionalTournament = tournamentHelperService.getNewTournament();
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(USER_DTO);
        if (optionalTournament.isPresent()) {
            tournamentHelperService.participate(user, optionalTournament.get());
            resp.sendRedirect(Route.HOME_PAGE);
        }
    }
}
