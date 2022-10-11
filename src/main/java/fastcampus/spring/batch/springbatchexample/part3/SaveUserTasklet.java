package fastcampus.spring.batch.springbatchexample.part3;

import fastcampus.spring.batch.springbatchexample.part4.Orders;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SaveUserTasklet implements Tasklet {

    private final int SIZE = 100;

    private final UserRepository userRepository;

    public SaveUserTasklet(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        List<User> users = createUsers();

        Collections.shuffle(users);

        userRepository.saveAll(users);  // 일일히 save 하는 것 보다 시간 절약

        return RepeatStatus.FINISHED;
    }

    private List<User> createUsers() {
        List<User> users = new ArrayList<>();

        for (int i = 0; i < SIZE; i++) {
            users.add(User.builder()
                            .orders(Collections.singletonList(Orders.builder()  // size 가 1로 고정된 리스트
                                    .amount(1_000)
                                    .createdDate(LocalDate.of(2020,11,1))
                                    .itemName("item" + i)
                                    .build()))
                            .username("test username" + i)
                            .build());
        }

        // SILVER 등급
        for (int i = 0; i < SIZE; i++) {
            users.add(User.builder()
                    .orders(Collections.singletonList(Orders.builder()  // size 가 1로 고정된 리스트
                            .amount(200_000)
                            .createdDate(LocalDate.of(2020,11,2))
                            .itemName("item" + i)
                            .build()))
                    .username("test username" + i)
                    .build());
        }

        // GOLD 등급
        for (int i = 0; i < SIZE; i++) {
            users.add(User.builder()
                    .orders(Collections.singletonList(Orders.builder()  // size 가 1로 고정된 리스트
                            .amount(300_000)
                            .createdDate(LocalDate.of(2020,11,3))
                            .itemName("item" + i)
                            .build()))
                    .username("test username" + i)
                    .build());
        }

        // VIP 등급
        for (int i = 0; i < SIZE; i++) {
            users.add(User.builder()
                    .orders(Collections.singletonList(Orders.builder()  // size 가 1로 고정된 리스트
                            .amount(500_000)
                            .createdDate(LocalDate.of(2020,11,4))
                            .itemName("item" + i)
                            .build()))
                    .username("test username" + i)
                    .build());
        }

        return users;
    }
}
