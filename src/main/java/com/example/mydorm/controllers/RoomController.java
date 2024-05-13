package com.example.mydorm.controllers;

import com.example.mydorm.models.Post;
import com.example.mydorm.models.Room;
import com.example.mydorm.models.User;
import com.example.mydorm.services.PostService;
import com.example.mydorm.services.RoomService;
import com.example.mydorm.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/room")
public class RoomController {
    List<User> users;

   @Autowired
    RoomService roomService;
   @Autowired
   UserService userService;
   @Autowired
   PostService postService; /*må jeg gerne have dem alle*/

@GetMapping("/")
public String rooms(Model model, HttpSession session){
   User user = (User)session.getAttribute("user");
   model.addAttribute("user", user);
   List<Room> rooms = roomService.getRooms(user.getId());
   model.addAttribute("rooms", rooms);
   return ("home/rooms_overview");
}

   @GetMapping("/new")
    public String create() {
       return "home/create_room";
   }

   @PostMapping("/new")
    public String insert(HttpSession session, @RequestParam String name, @RequestParam String bio){
       User admin = (User)session.getAttribute("user");
       roomService.createRoom(name,bio,admin);
       return "redirect:/";
   }

   @RequestMapping("/{id}")
    public String room(@PathVariable ("id") int roomId, Model model, HttpSession session) {
       User user = (User)session.getAttribute("user");
       model.addAttribute("user", user);
       Room room = roomService.getRoom(roomId, user.getId());
       model.addAttribute("room", room);
       List<Post> posts = postService.getPostsForRoom(roomId, user.getId());
       model.addAttribute("posts", posts);
       if (session.getAttribute("user") != null){
         return "home/room_feed";
      } else {
         return "redirect:/login";
      }
   }

   @GetMapping("/{id}/calender")
   public String calendar() {

   return null;
   }


   @GetMapping("{id}/edit")
    public String edit(HttpSession session, Model model, @PathVariable("id") int roomId){
    User user = (User)session.getAttribute("user");
    model.addAttribute("user", user);
    Room room = roomService.getRoom(roomId, user.getId());
    model.addAttribute(room);
       if (session.getAttribute("user") != null){
           return "home/room_edit";
       }
       else {
           return "redirect:/";
       }
   }
   @PostMapping("{id}/edit")
    public String edit(@PathVariable("id") int id, @RequestParam String name, @RequestParam String bio) {
    roomService.updateRoom(id, name, bio);
    /*lav slet funktion*/
    return "redirect:/room/{id}";
   }

   @GetMapping("{id}/invite")
   public String invite(@PathVariable("id") int id, Model model) {
    Room room = roomService.getRoom(id);
    model.addAttribute("room", room);
    model.addAttribute("users", users);
    return "home/room_invite";
   }

   @PostMapping("/{id}/invite")
    public String invite(@PathVariable("id") int id, Model model, @RequestParam String search) {
       List<User> users = userService.searchUsers(search);
       model.addAttribute("room", roomService.getRoom(id));
       model.addAttribute("users", users);
    return "home/room_invite";

}

    @PostMapping("/{id}/invite_users")
    public String inviteUsers(@PathVariable("id") int roomId, @RequestParam List<Integer> selectedUsers) {
        for (Integer userId : selectedUsers) {
            User user = userService.getUser(userId);
            // Gennemgår hvert rum, som brugeren er medlem af
            for (Room room : user.getRooms()) {
                // Tjekker om brugeren allerede er medlem af det ønskede rum
                if (room.getId() == roomId) {
                    return "redirect:/room/{id}";
                }
            }
            // Hvis brugeren ikke allerede er medlem af det ønskede rum, inviteres de
            roomService.inviteUsers(roomId, user);
        }
        return "redirect:/room/{id}";
    }


}
