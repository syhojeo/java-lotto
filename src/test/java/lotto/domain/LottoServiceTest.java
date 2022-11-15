package lotto.domain;

import lotto.model.Lotto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static camp.nextstep.edu.missionutils.test.Assertions.assertRandomUniqueNumbersInRangeTest;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class LottoServiceTest {

    LottoService lottoService;

    @BeforeEach
    void beforeEach() {
        lottoService = new LottoService();
    }

    @ParameterizedTest
    @DisplayName("로또 번호가 같은것이 있으면 true 리턴하는지 확인하는 단위 테스트")
    @CsvSource(value = {"1,true", "2,true", "3,true", "4,true", "5, true", "6, true", "7, false"})
    void checkLottoNumberTest(int winningLottoNumbers, boolean expected) {

        Lotto buyingLotto = new Lotto(List.of(1,2,3,4,5,6));

        boolean result = lottoService.checkLottoNumber(buyingLotto, winningLottoNumbers);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("로또 리스트 형식으로 생성이 되는지 단위 테스트")
    void generateLottoList() {

        int numberOfLotto = 5;
        List<Lotto> result = lottoService.generateLottoList(numberOfLotto);

        assertThat(result.size()).isEqualTo(numberOfLotto);
    }

    @Test
    @DisplayName("lotto 클래스로 생성이 되는지 단위 테스트")
    void generateLottoTest() {

        Lotto result = lottoService.generateLotto();

        assertThat(result).isInstanceOf(Lotto.class);
    }

    @Test
    @DisplayName("1~45 사이 6자리 랜덤값을 오름차순으로 정렬하는지 통합 테스트")
    void generateLottoNumbersTest() {

        List<Integer> result = lottoService.generateLottoNumbers();
        List<Integer> expected = lottoService.cloneList(result);
        Collections.sort(expected);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("깊은 복제 기능 테스트")
    void cloneListTest() {

        List<Integer> givenList = List.of(1,2,3,4,5,6);

        List<Integer> cloneList = lottoService.cloneList(givenList);

        assertThat(cloneList).isEqualTo(givenList);
    }

    @Test
    @DisplayName("발생한 로또 오름차순 정렬 테스트")
    void sortedLottoNumbersTest() {

        List<Integer> lottoNumbers = Arrays.asList(21, 34, 5, 22, 47, 43);

        lottoService.sortedLottoNumbers(lottoNumbers);

        assertThat(lottoNumbers).isEqualTo(Arrays.asList(5, 21, 22, 34, 43, 47));
    }

    @Test
    void 발행한_로또_유효성_검사_테스트() {
        final int testNumber = 100;

        for (int i = 0; i < testNumber; i++) {
            List<Integer> lottoNumbers = lottoService.generateLottoNumbers();
            for (int j = 0; j < 6; j++) {
                if (lottoNumbers.get(j) < 1 || lottoNumbers.get(j) > 45) {
                    fail();
                }
            }
        }
    }

    @Test
    void 발행한_로또_번호_길이_테스트() {
        final int testNumber = 100;

        for (int i = 0; i < testNumber; i++) {
            List<Integer> lottoNumbers = lottoService.generateLottoNumbers();
            assertThat(lottoNumbers.size()).isEqualTo(6);
        }
    }
}