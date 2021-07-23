package practiceA.BusinessCardManagerDao.cardSaveTest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import practiceA.BusinessCardManagerDao.domain.Card;
import practiceA.BusinessCardManagerDao.repository.CardRepository;
import practiceA.BusinessCardManagerDao.service.CardService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class JpaCardTest {

    @PersistenceContext private EntityManager em;
    @Autowired private CardRepository cardRepository;
    @Autowired private CardService cardService;

    @Test
    @DisplayName("jpa insert and get all results")
    public void AddAndGetAllTest() {
        // given
        Card card = new Card();
        card.setName("Hamging");
        card.setPhone("01012345678"); card.setCompany("samsung");
        Card card1 = new Card();
        card1.setName("Hamging22");
        card1.setPhone("01000000000"); card1.setCompany("naver");
        // when
        cardRepository.add(card);
        cardRepository.add(card1);
        List<Card> list = cardRepository.findAll();
        //then
        assertThat(card.getName()).isEqualTo(list.get(0).getName());
        for(Card temp : list) {
            System.out.println("id = " + temp.getId() +" name = " + temp.getName()+" phone = "+temp.getPhone() + " company = " + temp.getCompany());
        }
    }

    @Test
    @DisplayName("jpa insert and find by name")
    public void findByName() {
        // given
        Card card = new Card();
        card.setName("Hamging");
        card.setPhone("01012345678"); card.setCompany("samsung");
        Card card1 = new Card();
        card1.setName("Hamging22");
        card1.setPhone("01000000000"); card1.setCompany("naver");
        // when
        cardRepository.add(card);
        cardRepository.add(card1);
        List<Card> cards = cardRepository.findByName("Hamging22");
        //then
        assertThat("Hamging22").isEqualTo(cards.get(0).getName());
        System.out.println("Hamging22? = " + cards.get(0).getName());
    }

    @Test
    @DisplayName("jpa delete test")
    public void deleteById() {
        // given
        Card card = new Card();
        card.setName("Hamging");
        card.setPhone("01012345678"); card.setCompany("samsung");

        // when
        cardRepository.add(card);
        System.out.println("db size = " + cardRepository.findAll().size());
        System.out.println("card.getId() + card.getName() = " + card.getId() + card.getName());
        cardRepository.deleteCard(card.getId());

        //then
        System.out.println("db size = " + cardRepository.findAll().size());
    }

    @Test
    @Transactional
    @DisplayName("jpa contains entity?")
    public void checkPersistency() {
        Card card = new Card();
        card.setName("Hamging"); card.setPhone("01012345678"); card.setCompany("samsung");
        // when
        cardRepository.add(card);
        System.out.println(cardRepository.findByName(card.getName()));
        System.out.println(em.contains(card));
    }
}