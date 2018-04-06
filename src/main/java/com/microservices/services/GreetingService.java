package com.microservices.services;

import com.example.server.GreetingServiceGrpc;
import com.example.server.HelloRequest;
import com.example.server.HelloResponse;
import io.grpc.stub.StreamObserver;

import java.util.*;

public class GreetingService extends GreetingServiceGrpc.GreetingServiceImplBase {

    private static Map<HelloRequest.MusicGenres, List<String>> songsMap = new HashMap<>();

    static {
        songsMap.put(HelloRequest.MusicGenres.JAZZ, Arrays.asList(
                "Take Five - Dave Brubeck",
                "What a Wonderful World - Louis Armstrong",
                "Acknowledgment - John Coltrane Quartet",
                "My Favorite Things - Dave Brubeck",
                "All Blues - Kenny Burrell",
                "Birdland - Maynard Ferguson",
                "Strange Fruit - Nina Simone",
                "Sing, Sing, Sing - Benny Goodman and His Orchestra",
                "Song for My Father - Hermao Feriera",
                "So What - Miles Davis"
        ));

        songsMap.put(HelloRequest.MusicGenres.METAL, Arrays.asList(
                "Master of Puppets - Metallica",
                "Hallowed Be Thy Name - Iron Maiden",
                "Holy Wars... The Punishment Due - Megadeth",
                "One - Metallica",
                "Painkiller - Judas Priest",
                "Paranoid - Black Sabbath",
                "The Trooper - Iron Maiden",
                "Enter Sandman - Metallica",
                "Holy Diver - Dio",
                "Iron Man - Black Sabbath"
        ));

        songsMap.put(HelloRequest.MusicGenres.OTHER, Arrays.asList(
                "Bohemian Rhapsody - Queen",
                "Stairway to Heaven - Led Zeppelin",
                "Imagine - John Lennon",
                "Smells Like Teen Spirit - Nirvana",
                "Hotel California - Eagles",
                "One - Metallica. ...\n",
                "Comfortably Numb - Pink Floyd",
                "Like a Rolling Stone - Bob Dylan",
                "Sweet Child O'Mine - Guns N' Roses",
                "Hey Jude - The Beatles"
        ));

        songsMap.put(HelloRequest.MusicGenres.POP, Arrays.asList(
                "“Thriller” by Michael Jackson",
                "“Like a Prayer” by Madonna",
                "“When Doves Cry” by Prince",
                "“I Wanna Dance With Somebody” by Whitney Houston",
                "“Baby One More Time” by Britney Spears",
                "“It’s Gonna Be Me” by ‘N Sync",
                "“Everybody (Backstreet’s Back)” by the Backstreet Boys",
                "“Rolling in the Deep” by Adele",
                "“Don’t Stop Believing” by Journey",
                "“Billie Jean” by Michael Jackson"
        ));

        songsMap.put(HelloRequest.MusicGenres.ROCK, Arrays.asList(
                "LED ZEPPELIN - STAIRWAY TO HEAVEN",
                "QUEEN - BOHEMIAN RHAPSODY",
                "LYNYRD SKYNYRD - FREE BIRD",
                "DEEP PURPLE - SMOKE ON THE WATER",
                "PINK FLOYD - COMFORTABLY NUMB",
                "LED ZEPPELIN - KASHMIR",
                "RAINBOW - STARGAZER",
                "FREE - ALL RIGHT NOW",
                "LED ZEPPELIN - WHOLE LOTTA LOVE",
                "THE WHO - WON'T GET FOOLED AGAIN"
        ));
    }

    @Override
    public void greeting(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {

        HelloResponse response = HelloResponse.newBuilder().build();

        try {
            String suggestedSong = getSuggestedSong(request.getAge(), new ArrayList<>(request.getGenreList()));

            String greeting = "Hello %s, you should try this song [%s]";
            String greetingFormat = String.format(greeting, request.getName(), suggestedSong);

            response = HelloResponse.newBuilder().setGreeting(greetingFormat).build();


        } catch (Throwable t) {
            response = HelloResponse.newBuilder().setGreeting("ERROR!!!").build();
            t.printStackTrace();
        }finally {
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }

    private String getSuggestedSong(int age, List<HelloRequest.MusicGenres> musicGenres) {
        Collections.shuffle(musicGenres);
        HelloRequest.MusicGenres genere = musicGenres.stream().findFirst().get();

        return getRandomSongByAge(genere, age);
    }

    private String getRandomSongByAge(HelloRequest.MusicGenres genere, int age) {
        ArrayList<String> songs = new ArrayList<>(songsMap.get(genere));
        Collections.shuffle(songs);
        return songs.stream().findFirst().get();
    }
}
