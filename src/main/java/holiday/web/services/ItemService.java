package holiday.web.services;

import holiday.web.controllers.ItemController;
import holiday.web.entities.Item;
import holiday.web.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * Created by karthik on 1/22/2017.
 */

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public List<Item> getAllItems(Long itemId)
    {
        return StreamSupport.stream(itemRepository.findByCheckoutOrderOrderId(itemId).spliterator(),false)
                .map(c -> {c.add(linkTo(methodOn(ItemController.class).getAllItems(itemId)).withSelfRel());return c;})
                .collect(Collectors.toList());
    }

    public Item getItem(Long itemId)
    {
        Item c = itemRepository.findOne(itemId);
        c.add(linkTo(methodOn(ItemController.class).getAllItems(itemId)).withSelfRel());
        c.add(linkTo(methodOn(ItemController.class).updateItem(c)).withRel("update"));
        return c;
    }

    public Item saveItem(Item item)
    {
        Item c = itemRepository.save(item);
        c.add(linkTo(methodOn(ItemController.class).getAllItems(c.getItemID())).withSelfRel());
        c.add(linkTo(methodOn(ItemController.class).updateItem(c)).withRel("update"));
        return c;
    }



    public void deleteItem(Item item)
    {
        itemRepository.delete(item.getItemID());
    }
}
