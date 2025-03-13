package com.example.demo.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.demo.entity.Memo;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class MemoRepositoryTests {

    @Autowired
    MemoRepository memoRepository;

    @Test
    public void testClass(){
        System.out.println(memoRepository.getClass().getName());
    }

    // 더미 데이터 100개 삽입
    @Test
    public void testInsertDummies() {
        IntStream.rangeClosed(1,100).forEach(i->{
            Memo memo = Memo.builder().memoText("Sample..." + i).build();
            memoRepository.save(memo);
        });
    }

    @Test
    public void testSelect() {
        Optional<Memo> result = memoRepository.findById(getLastMno());

        System.out.println("==================================");

        if(result.isPresent()) {
            Memo memo = result.get();
            System.out.println(memo);
        } else {
            System.out.println("Memo not found.");
        }
    }

    @Transactional
    @Test
    public void testSelect2() {
        Long mno = getLastMno(); // 마지막 ID 가져오기

        Memo memo = memoRepository.findById(mno)
                .orElseThrow(() -> new RuntimeException("Memo with ID " + mno + " not found."));

        System.out.println("===============================");
        System.out.println(memo);
    }

    @Transactional
    @Test
    public void testUpdate() {
        Long mno = getLastMno(); // 존재하는 가장 큰 ID 가져오기

        Optional<Memo> result = memoRepository.findById(mno);
        if(result.isPresent()) {
            Memo memo = result.get();
            memo.setMemoText("Update Text");
            memoRepository.save(memo);
            System.out.println("Updated Memo: " + memo);
        } else {
            System.out.println("Memo not found.");
        }
    }

    @Transactional
    @Test
    public void testDelete() {
        Long mno = getLastMno(); // 존재하는 마지막 ID 가져오기

        if(memoRepository.existsById(mno)) {
            memoRepository.deleteById(mno);
            System.out.println("Deleted Memo with ID " + mno);
        } else {
            System.out.println("Memo not found.");
        }
    }

    /**
     * 📌 현재 가장 마지막 `mno`를 조회하는 메서드
     */
    private Long getLastMno() {
        return memoRepository.findTopByOrderByMnoDesc()
                .map(Memo::getMno)
                .orElseThrow(() -> new RuntimeException("No Memo exists in DB"));
    }
}
