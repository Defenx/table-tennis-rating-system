package servlet.tournament;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import listener.ContextListener;
import org.hibernate.SessionFactory;
import service.tournament.TournamentService;
import service.extension.ExtensionVariableTypeResolver;

public class BaseTournamentServlet extends HttpServlet {
    protected TournamentService tournamentService;
    protected SessionFactory sessionFactory;
    protected ExtensionVariableTypeResolver extensionVariableTypeResolver;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        var servletContext = config.getServletContext();
        extensionVariableTypeResolver = (ExtensionVariableTypeResolver) servletContext.getAttribute(ContextListener.EXTENSION_VARIABLE_TYPE_RESOLVER);
        tournamentService = (TournamentService) servletContext.getAttribute(ContextListener.TOURNAMENT_SERVICE);
        sessionFactory = (SessionFactory) servletContext.getAttribute(ContextListener.SESSION_FACTORY);
    }
}
