package jakub.kask.lab.business.events;

import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Qualifier;
import java.lang.annotation.*;

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
public @interface IncomeSortAsc {
    Annotation Literal = new AnnotationLiteral<IncomeSortAsc>() { };
}