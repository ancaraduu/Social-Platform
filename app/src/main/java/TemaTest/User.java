package TemaTest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class User{
    final private String username;                         // user username
    final private String password;                  // user password (private)
    private final User[] followers = new User[200];        // array of users that follow this user
    private int counterFollowers;                    // number of followers
    final private User[] following = new User[200]; // array of users that are followed by this user
    private int counterFollowing;                   // number of users this user is following
    private final Post[] posts = new Post[200];            // array of posts by this user
    private int counterPost;                         // number of posts by this user
    boolean used;   // Helps with finding certain users in certain methods

    User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public int getCounterFollowers(){
        return counterFollowers;
    }

    public int getCounterPost(){
        return counterPost;
    }

    public Post[] getPosts(){
        return posts;
    }
    Post findPost(int id) {
        Post thePost = null;
        for(int i = 0; i < counterPost; i++) {
            if (posts[i].getId() == id) {
                thePost = posts[i];
                break;
            }
        }
        return thePost;
    }

    void createPost(String text) {
        posts[counterPost] = new Post(username, text);
        this.counterPost++;
        System.out.println("{'status':'ok','message':'Post added successfully'}");
    }

    void deletePost(Post thePost){
        for(int i = thePost.getId(); i < this.counterPost; i++)
            if(posts[i + 1] != null)
                posts[i] = posts[i + 1];

        // Lose the reference to the deleted post
        posts[counterPost - 1] = null;

        // Decrement the number of posts
        this.counterPost--;
        System.out.println("{'status':'ok','message':'Post deleted successfully'}");
    }

    boolean checkIfFollowing(User theUserToFollow) {
        // You cannot follow yourself
        if(username.equals(theUserToFollow.username))
            return true;

        for(int i = 0; i < this.counterFollowing; i++)
            if (this.following[i].equals(theUserToFollow)) {
                return true;
            }
        return false;
    }

    void follow(User theUserToFollow) {
        // Add the user to follow to this user's array of followers
        this.following[this.counterFollowing] = theUserToFollow;
        this.counterFollowing++;

        // Add this user to the user to follow array of users that follow him
        theUserToFollow.followers[theUserToFollow.counterFollowers] = this;
        theUserToFollow.counterFollowers++;
        System.out.println("{'status':'ok','message':'Operation executed successfully'}");
    }

    void unfollow(User theUserToUnfollow) {
        int index = 0;

        // Remove the user to unfollow from this user's array of users he is following
        for(int i = 0; i < counterFollowing; i++)
            if (following[i].equals(theUserToUnfollow)) {
                index = i;
                break;
            }
        for(int i = index; i < counterFollowing - 1; i++)
            following[i] = following[i + 1];
        this.counterFollowing--;

        // Remove this user from the user to unfollow array of followers
        for(int i = 0; i < theUserToUnfollow.counterFollowers; i++)
            if (theUserToUnfollow.followers[i].equals(this)) {
                index = i;
                break;
            }
        for(int i = index; i < theUserToUnfollow.counterFollowers - 1; i++)
            theUserToUnfollow.followers[i] = theUserToUnfollow.followers[i + 1];
        theUserToUnfollow.counterFollowers--;

        System.out.println("{'status':'ok','message':'Operation executed successfully'}");
    }

    void getFollowingsPosts(){
        System.out.print("{ 'status' : 'ok', 'message' : [");

        // Get the date format
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        String currentDateAsString = dateFormat.format(date);

        for(int t = App.idMakerPost; t >= 1; t--)                   // Go from the last added post to the first one
            for(int i = 0; i < this.counterFollowing; i++) {    // Go through all the users this user is following
                int ok = 0;
                for (int j = 0; j < this.following[i].counterPost; j++)     // Go through all the posts of all the users this user is following
                    if(this.following[i].posts[j].getId() == t) {
                        if(this.following[i].posts[j].getId() == App.idMakerPost)
                            System.out.print("{'post_id' : " + "'" + this.following[i].posts[j].getId() + "'" + ", 'post_text' : " + this.following[i].posts[j].getText() + ", 'post_date' : '" + currentDateAsString + "', 'username' : " + this.following[i].posts[j].getUsername() + "}");
                        else
                            System.out.print(",{'post_id' : " + "'" + this.following[i].posts[j].getId() + "'" + ", 'post_text' : " + this.following[i].posts[j].getText() + ", 'post_date' : '" + currentDateAsString + "', 'username' : " + this.following[i].posts[j].getUsername() + "}");
                        ok = 1;
                        break;
                    }
                if(ok == 1)
                    break;
            }
        System.out.println("]}");
    }

    void getFollowing(){
        System.out.print("{ 'status' : 'ok', 'message' : [");

        for(int i = 0; i < this.counterFollowing; i++) {    // Go through all the users this user is following
            if (i == 0)
                System.out.print(this.following[i].username);
            else
                System.out.print("," +this.following[i].username);  // Print their usernames
        }
        System.out.println("]}");
    }

    void getFollowers(){
        System.out.print("{ 'status' : 'ok', 'message' : [");

        for(int i = 0; i < this.counterFollowers; i++) {    // Go through all the followers of this user
            if (i == 0)
                System.out.print(this.followers[i].username);
            else
                System.out.print("," +this.followers[i].username);  // Print their usernames
        }
        System.out.println("]}");
    }

    void listPosts(){
        System.out.print("{ 'status' : 'ok', 'message' : [");

        // Get the date format
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        String currentDateAsString = dateFormat.format(date);

        for (int j = counterPost - 1; j >= 0; j--) {    // Go through all the posts of this user from the last to the first one
            if(j == counterPost - 1)
                System.out.print("{'post_id' : " + "'" + posts[j].getId() + "'" + ", 'post_text' : " + posts[j].getText() + ", 'post_date' : '" + currentDateAsString + "'}");
            else
                System.out.print(",{'post_id' : " + "'" + posts[j].getId() + "'" + ", 'post_text' : " + posts[j].getText() + ", 'post_date' : '" + currentDateAsString + "'}");
        }
        System.out.println("]}");
    }

}
