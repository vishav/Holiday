package holiday.web.services;

import holiday.web.entities.Item;
import holiday.web.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by karthik on 1/22/2017.
 */

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public Item saveItem(Item item)
    {
        Item c = itemRepository.save(item);
        return c;
    }
}
