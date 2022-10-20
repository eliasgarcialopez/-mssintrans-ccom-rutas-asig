package mx.gob.imss.mssistrans.ccom.rutas.util;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class ControllersUtil {

    /**
     * Prepara el sort para la consulta con paginaci&oacute;n.
     *
     * @param sort
     * @return
     */
    public static List<Sort.Order> convertSort(String[] sort) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sort[0].contains(",")) {
            for (String string : sort) {
                String[] strings = string.split(",");
                orders.add(new Sort.Order(Sort.Direction.fromString(strings[1]), strings[0]));
            }
        } else {
            orders.add(new Sort.Order(Sort.Direction.fromString(sort[1]), sort[0]));
        }
        return orders;
    }

}
