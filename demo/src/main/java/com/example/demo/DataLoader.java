package com.example.demo; // 프로젝트의 메인 패키지 또는 data 패키지에 맞게 수정하세요

import com.example.demo.domain.Book;
import com.example.demo.domain.Review;
import com.example.demo.domain.User;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.ReviewRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.enums.Category;
import com.example.demo.enums.Role; // Role Enum이 있다면 필요합니다.

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class DataLoader implements ApplicationRunner {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final ReviewRepository reviewRepository;
    private final Random random = new Random();

    // 생성자 주입 (Spring이 자동으로 Repository 빈을 주입합니다)
    public DataLoader(UserRepository userRepository, BookRepository bookRepository, ReviewRepository reviewRepository) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        // 데이터가 이미 존재하면 (User 레코드가 0보다 크면) DataLoader를 건너뜁니다.
        // 이 로직은 이전 세션에서 발생했던 'Duplicate entry' 오류를 방지합니다.
        if (userRepository.count() > 0) {
            System.out.println("✅ 기존 데이터가 감지되어 DataLoader 시드 생성을 건너뜁니다.");
            return;
        }

        System.out.println("--- 🚀 데이터 초기 로딩 시작 (200건 이상) ---");

        // 1. User 10건 생성 (최소 2개 Role 요구사항 충족)
        List<User> users = createUsers(10);
        userRepository.saveAll(users);
        System.out.printf("✅ User Data Loaded: %d records\n", users.size());

        // 2. Book 50건 생성
        List<Book> books = createBooks(50);
        bookRepository.saveAll(books);
        System.out.printf("✅ Book Data Loaded: %d records\n", books.size());

        // 3. Review 150건 생성 (총 10 + 50 + 150 = 210건, 200건 이상 요구사항 충족)
        List<Review> reviews = createReviews(150, users, books);
        reviewRepository.saveAll(reviews);
        System.out.printf("✅ Review Data Loaded: %d records\n", reviews.size());

        System.out.println("--- 🥳 모든 시드 데이터 로딩 완료 ---");
    }

    // =========================================================
    // 데이터 생성 도우미 메서드 (실제 데이터를 만드는 로직)
    // =========================================================

    private List<User> createUsers(int count) {
    List<User> users = new ArrayList<>();
    
    // Enum 클래스는 사용자의 실제 이름에 맞게 수정해주세요 (예: Role, Gender)
    // Gender 클래스가 없다면 'String'으로 설정하거나 필드를 제거하세요.
    // enum Gender { MALE, FEMALE }
    
    for (int i = 0; i < count; i++) {
        User user = new User();
        
        // 1. 문자열/계정 관련 필수 필드 (확인된 user_name 포함)
        user.setUserName("SeederUser" + i); 
        user.setUserEmail("user" + i + "@example.com"); // 💡 필수 필드 #1: user_email
        user.setPassword("dummyPassword" + i);           // 💡 필수 필드 #2: password (실제로는 암호화해야 함)

        // 2. 주소/연락처 관련 필수 필드
        String phoneNumber = String.format("010-1234-%04d", i);
        user.setPhoneNumber(phoneNumber); 
        
        String address = "Seoul, Gangnam-gu " + (100 + i) + " Street";
        user.setUserAddress(address); 
        
        // 3. 날짜/상태/역할 관련 필수 필드
        user.setRole(i == 0 ? Role.ROLE_ADMIN : Role.ROLE_USER);
        user.setCreatedAt(LocalDateTime.now().minusDays(count - i));
        
        user.setActive(true); // 💡 필수 필드 #3: is_active (boolean)
        
        // 💡 필수 필드 #4: gender (Enum 타입이라고 가정)
        // 만약 Gender Enum이 없다면 user.setGender("MALE") 등으로 String 값을 설정해야 합니다.
        user.setGender(i % 2 == 0 ? "MALE" : "FEMALE"); 

        // 💡 필수 필드 #5: birth_date (LocalDate 타입이라고 가정)
        user.setBirthDate(LocalDate.of(1990 + (i % 30), 1, 1)); 

        users.add(user);
    }
    return users;
}

    private List<Book> createBooks(int count) {
        List<Book> books = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            Book book = new Book();
            book.setTitle("Book Title " + i);
            book.setAuthor("Author " + (i % 5 + 1)); // 5명의 저자로 분산
            book.setIsbn("978-89-" + String.format("%03d", i) + "-1234-5");
            book.setPrice(15000 + (i * 100));
            book.setPublisher("출판사 A" + (i % 4 + 1));
            book.setPublicationDate(LocalDate.now().minusDays(i * 10L));
            Category category = (i % 3 == 0) ? Category.FICTION : 
                    ((i % 3 == 1) ? Category.NON_FICTION : Category.SCIENCE);
            book.setCategory(category);
            book.setCreatedAt(LocalDateTime.now());
            book.setSummary("이 책은 훌륭한 요약 내용입니다.");
            book.setUpdatedAt(LocalDateTime.now().minusDays(count - i).plusMinutes(10));
            books.add(book);
        }
        return books;
    }

    private List<Review> createReviews(int count, List<User> users, List<Book> books) {
        List<Review> reviews = new ArrayList<>();
        int userCount = users.size();
        int bookCount = books.size();

        for (int i = 1; i <= count; i++) {
            User randomUser = users.get(random.nextInt(userCount));
            Book randomBook = books.get(random.nextInt(bookCount));

            Review review = new Review();
            review.setRating(random.nextInt(5) + 1); // 1~5점
            review.setContent("This is review content " + i + " for book " + randomBook.getBookId());
            
            // 외래 키 설정
            review.setUser(randomUser);
            review.setBook(randomBook);

            review.setCreatedAt(LocalDateTime.now());
            reviews.add(review);
        }
        return reviews;
    }
}