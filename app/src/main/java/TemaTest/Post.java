package TemaTest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Post implements Likeable{
    private final String username;                    // The username of the user that added the post
    private final int id;                             // The id of the post
    private final String text;                        // The text of the post
    private final User[] liked = new User[200];       // Array of users that liked the post
    private int counterLiked;                   // Number of users that liked the post
    private final Comm[] comments = new Comm[200];    // Array of users that added a comment to the post
    private int counterComments;                // Number of users that added a comment to the post
    boolean used;

    // Getters
    public String getUsername(){
        return username;
    }

    public int getId(){
        return id;
    }

    public String getText(){
        return text;
    }

    public int getCounterLiked(){
        return counterLiked;
    }

    public int getCounterComments(){
        return counterComments;
    }

    public Comm[] getComments(){
        return comments;
    }
    Post(String username, String text) {
        App.idMakerPost++;
        id = App.idMakerPost;
        this.username = username;
        this.text = text;
    }

    boolean checkIfLiked(User theUser) {
        boolean like = false;
        for (int i = 0; i < counterLiked; i++)
            if (liked[i].equals(theUser)) {
                like = true;
                break;
            }
        return like;
    }

    public void like(User theUser) {
        if (theUser.getUsername().equals(this.username)) {
            System.out.println("{ 'status' : 'error', 'message' : 'The post identifier to like was not valid'}");
        } else {
            // Add the user to this post's array of users that liked it
            liked[counterLiked] = theUser;
            counterLiked++;
            System.out.println("{'status':'ok','message':'Operation executed successfully'}");
        }
    }

    public void unlike(User theUser) {
        // Remove the user from this post's array of users that liked it
        int index = 0;
        for(int i = 0; i < this.counterLiked; i++)
            if (this.liked[i].equals(theUser)) {
                index = i;
                break;
            }
        for(int i = index; i < this.counterLiked - 1; i++)
            this.liked[i] = this.liked[i + 1];
        this.liked[this.counterLiked - 1] = null;
        this.counterLiked--;

        System.out.println("{'status':'ok','message':'Operation executed successfully'}");
    }

    void commentPost(User theUser, String text){
        // Create a new comment with the text provided
        // Add it to this post's array of comments
        comments[this.counterComments] = new Comm(theUser.getUsername(), text);
        this.counterComments++;
        System.out.println("{'status':'ok','message':'Comment added successfully'}");
    }

    void deleteComment(Comm theComment){
        // Remove the comment from this post's array of comments
        for(int i = theComment.getId(); i < this.counterComments; i++)
            if(comments[i + 1] != null)
                comments[i] = comments[i + 1];
        comments[this.counterComments - 1] = null;
        this.counterComments--;
        System.out.println("{'status':'ok','message':'Operation executed successfully'}");
    }

    // List this post's comments into a string
    String postCommentsString(){
        // Get the date format
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        String currentDateAsString = dateFormat.format(date);

        StringBuilder ret = new StringBuilder("'comments' : [");
        for(int i = 0; i < this.counterComments; i++)
            ret.append("{'comment_id' : '").append(comments[i].getId()).append("','comment_text' : ").append(comments[i].getText()).append(", 'comment_date' : '").append(currentDateAsString).append("', ").append("'username' : ").append(comments[i].getUsername()).append(", 'number_of_likes' : '").append(comments[i].getCounterLiked()).append("'}");
        ret.append("] ");
        return ret.toString();
    }
    void getPostDetails(){
        System.out.print("{ 'status' : 'ok', 'message' : [");

        // Get date format
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        String currentDateAsString = dateFormat.format(date);

        System.out.print("{'post_text' : " + this.text + ", 'post_date' : '" + currentDateAsString + "', 'username' : " + this.username + ", 'number_of_likes' :'" + this.counterLiked + "'," + this.postCommentsString() + "}] ");
        System.out.println("}");
    }

}