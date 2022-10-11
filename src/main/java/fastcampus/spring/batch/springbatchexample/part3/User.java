package fastcampus.spring.batch.springbatchexample.part3;

import fastcampus.spring.batch.springbatchexample.part4.Orders;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.dynamic.loading.InjectionClassLoader;
import org.aspectj.weaver.ast.Or;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @Enumerated(EnumType.STRING)
    private Level level = Level.NORMAL;

    //private int totalAmount;

    @OneToMany(cascade = CascadeType.PERSIST)      // user 가 저장되면서 order 도 같이 저장 될 수 있도록 영속성 전이를 적용
    @JoinColumn(name = "user_id")
    private List<Orders> orders;

    private LocalDate updatedDate;

    @Builder
    public User(String username, List<Orders> orders) {
        this.username = username;
        this.orders = orders;
    }   // 이 생성자를 기준으로 생성


    private int getTotalAmount(){
        return this.orders.stream().mapToInt(Orders::getAmount).sum();
    }

    public boolean availableLevelUp(){  // level up 가능 여부
        if(this.getLevel() == null)
            return false;

        else if (this.getLevel().nextLevel == null) // 이미 VIP 인 경우, LevelUp 할 필요가 없음
            return false;

        return this.getTotalAmount() >= this.getLevel().nextAmount;

    }

    public void levelUp() {
        Level nextLevel = Level.getNextLevel(this.getTotalAmount());
        this.level = nextLevel;
        this.updatedDate = LocalDate.now();

    }

    public enum Level{
        VIP(500_000, null),
        GOLD(500_000, VIP),
        SILVER(300_000, GOLD),
        NORMAL(200_000, SILVER);

        private final int nextAmount;

        private final Level nextLevel;

        Level(int nextAmount, Level nextLevel){
            this.nextAmount = nextAmount;
            this.nextLevel = nextLevel;
        }



        static Level getNextLevel(int totalAmount) {    // 유저가 현재 갖고 있는 totalAmount
            if(totalAmount >= Level.VIP.nextAmount) {
                return VIP;
            }

            // GOLD.nextLevel == VIP
            if(totalAmount >= Level.GOLD.nextAmount) {
                return GOLD.nextLevel;  // == VIP
            }

            // SILVER.nextLevel == GOLD
            if(totalAmount >= Level.SILVER.nextAmount) {
                return SILVER.nextLevel;
            }

            // NORMAL.nextLevel == SILVER
            if(totalAmount >= Level.NORMAL.nextAmount) {
                return NORMAL.nextLevel;
            }

            return NORMAL;
        }


    }
}
