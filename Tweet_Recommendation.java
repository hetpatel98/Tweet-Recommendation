package com.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tweet_Recommendation {
    //Just implementing the below method assuming we have this method being called from some main function

    static int[] getRecommendedTweets(int[][] followGraph_edges, int[][] likeGraph_edges,
                                      int targetUser, int minLikeThreshold) {


        List<Integer> followedUsers = new ArrayList<Integer>();

        //Extract followings of the target user into a List
        for (int j=0; j<followGraph_edges.length; j++){
            if (followGraph_edges[j][0]==targetUser){
                followedUsers.add(followGraph_edges[j][1]);
            }
        }

        //HashMap to keep track of TweetID and Likes
        Map<Integer, Integer> tweetLikes = new HashMap<Integer, Integer>();


        for (int q=0; q<likeGraph_edges.length; q++){
            Integer userId = likeGraph_edges[q][0];
            Integer tweetId = likeGraph_edges[q][1];
            if (followedUsers.contains(userId)){ //if the tweet is Liked by one of the Target User's followings then...
                if(!tweetLikes.containsKey(tweetId)){ //add the TweetID to the hashmap if it doesn't exist already
                    tweetLikes.put(tweetId, 0);
                }
                tweetLikes.put(tweetId, tweetLikes.get(tweetId)+1);//if exists, increment the Like count for that TweetID by 1
            }
        }

        List<Integer> suggestedTweets = new ArrayList<Integer>();
        for(Integer tweetId : tweetLikes.keySet()){
            if(tweetLikes.get(tweetId) >= minLikeThreshold){ //if the TweetID in the hashmap has >= minLikeThreshold number of Likes then...
                suggestedTweets.add(tweetId); //add the TweedID to the suggestedTweets List
            }
        }

        Collections.sort(suggestedTweets);
        return suggestedTweets.stream().mapToInt(i->i).toArray(); //return the suggestedTweets as an int[]
    }

}
