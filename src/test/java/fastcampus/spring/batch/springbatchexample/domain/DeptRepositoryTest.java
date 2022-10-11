package fastcampus.spring.batch.springbatchexample.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import java.util.List;

@SpringBootTest
public class DeptRepositoryTest {

    @Autowired
    DeptRepository deptRepository;

    @Test
    @Commit
    public void dept01(){
        Dept dept = new Dept();

        dept.setUserID("faeq");
        deptRepository.save(dept);

        Dept dept2 = new Dept();
        dept2.setUserID("abcd");
        deptRepository.save(dept2);


        List<Dept> deptList = (List<Dept>) deptRepository.findAll();
        

        Dept dept3 = deptList.stream().filter(dept1 -> dept1.getId().equals(1L)).findFirst().orElseThrow(() -> new IllegalArgumentException());

        System.out.println(dept3);

    }


}