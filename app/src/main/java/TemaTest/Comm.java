package TemaTest;

public class Comm implements Likeable {
    private final String username;                // The username of the user that added the comment
    private final int id;                         // The id of the comment
    private final String text;                    // The text of the comment
    private final User[] liked = new User[200];   // Array of users that liked the comment
    private int counterLiked;               // Number of users that liked the comment

    Comm(String username, String text){
        App.idMakerComm++;
        id = App.idMakerComm;
        this.username = username;
        this.text = text;
    }

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
    public void like(User theUser) {
        if (theUser.getUsername().equals(this.username)) {
            System.out.println("{ 'status' : 'error', 'message' : 'The comment identifier to like was not valid'}");
        } else {
            // Add the user to this comment's array of users that liked it
            liked[counterLiked] = theUser;
            counterLiked++;
            System.out.println("{'status':'ok','message':'Operation executed successfully'}");
        }
    }

    public void unlike(User theUser) {
        // Remove the user from this comment's array of users that liked it
        int j = 0;
        for(int i = 0; i < counterLiked - 1; i++)
            if (liked[i] != theUser) {
                liked[j] = liked[i];
                j++;
            }
        counterLiked--;
        System.out.println("{'status':'ok','message':'Operation executed successfully'}");
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
}