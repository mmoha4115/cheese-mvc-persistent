package org.launchcode.models.data;

import org.launchcode.models.Menu;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Mohamed Mohamed
 */

public interface MenuDao extends CrudRepository<Menu,Integer> {
}
