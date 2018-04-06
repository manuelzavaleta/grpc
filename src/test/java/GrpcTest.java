import com.example.server.GreetingServiceGrpc;
import com.example.server.HelloRequest;
import com.example.server.HelloResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class GrpcTest {

    @Test
    public void clientTest() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9080)
                .usePlaintext(true)
                .build();

        GreetingServiceGrpc.GreetingServiceBlockingStub stub = GreetingServiceGrpc.newBlockingStub(channel);
        String name = "Manuel";
        int age = 32;

        HelloResponse response = stub.greeting(HelloRequest.newBuilder()
                .setName(name)
                .setAge(age)
                .addGenre(HelloRequest.MusicGenres.METAL)
                .addGenre(HelloRequest.MusicGenres.ROCK)
                .build());

        System.out.print(response);
        assertTrue(response.getGreeting().contains(name));
    }
}
