package servlet.tournament;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import listener.ContextListener;
import service.TournamentService;
import service.tournament.running.ExtensionVariableTypeResolver;

public class BaseTournamentServlet extends HttpServlet {
    protected TournamentService tournamentService;
    protected ExtensionVariableTypeResolver extensionVariableTypeResolver;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        var servletContext = config.getServletContext();
        extensionVariableTypeResolver = (ExtensionVariableTypeResolver) servletContext.getAttribute(ContextListener.EXTENSION_VARIABLE_TYPE_RESOLVER);
        tournamentService = (TournamentService) servletContext.getAttribute(ContextListener.TOURNAMENT_SERVICE);
    }
}
