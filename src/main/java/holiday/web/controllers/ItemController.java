package holiday.web.controllers;

import holiday.web.entities.Item;
import holiday.web.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by karthik on 1/22/2017.
 */

@RestController
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping("/orders/{orderId}/items")
    public List<Item> getAllItems(@PathVariable Long orderId)
    {
        return itemService.getAllItems(orderId);
    }

    @RequestMapping("/items/{id}")
    public Item getItem(@PathVariable Long id)
    {
        return itemService.getItem(id);
    }

    @RequestMapping(method = RequestMethod.POST,value = "/items")
    public Item addItem(@RequestBody Item item)
    {
        return itemService.saveItem(item);
    }

    @RequestMapping(method = RequestMethod.PUT,value = "/items")
    public Item updateItem(@RequestBody Item item)
    {
        return itemService.saveItem(item);
    }

    @RequestMapping(method = RequestMethod.DELETE,value = "/items")
    public void deleteItem(Item item)
    {
        itemService.deleteItem(item);
    }
}
