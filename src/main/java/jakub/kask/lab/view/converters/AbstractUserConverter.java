package jakub.kask.lab.view.converters;

import jakub.kask.lab.business.boundary.UserService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.util.function.BiFunction;
import java.util.function.Function;

abstract class AbstractUserConverter<T> implements Converter<T> {

    @Inject
    UserService userService;

    private BiFunction<UserService, Integer, T> retrieveFunction;

    private Function<T, Integer> idExtractor;

    AbstractUserConverter(Function<T, Integer> idExtractor, BiFunction<UserService, Integer, T> retrieveFunction) {
        this.retrieveFunction = retrieveFunction;
        this.idExtractor = idExtractor;
    }

    @Override
    public T getAsObject(FacesContext context, UIComponent component, String value) {
        T entity = retrieveFunction.apply(userService, Integer.parseInt(value));

        if (entity == null) {
            context.getExternalContext().setResponseStatus(HttpServletResponse.SC_NOT_FOUND);
            context.responseComplete();
        }

        return entity;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, T value) {
        Integer id = idExtractor.apply(value);
        return id != null ? id.toString() : null;
    }
}
