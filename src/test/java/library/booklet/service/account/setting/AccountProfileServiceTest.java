package library.booklet.service.account.setting;

import library.booklet.dto.AccountProfileDTO;
import library.booklet.entity.AccountProfileEntity;
import library.booklet.mapper.AccountProfileMapper;
import library.booklet.repository.AccountProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AccountProfileServiceTest {

    AccountProfileService accountProfileService;
    AccountProfileRepository accountProfileRepository;
    AccountProfileMapper accountProfileMapper;

    @BeforeEach
    void setUp() {
        accountProfileRepository = mock(AccountProfileRepository.class);
        accountProfileMapper = mock(AccountProfileMapper.class);
        accountProfileService = new AccountProfileService(accountProfileRepository,
                accountProfileMapper);
    }

    @Test
    void saveProfileSettings() {
        AccountProfileDTO accountProfileDTO = new AccountProfileDTO();
        AccountProfileEntity accountProfileEntity = new AccountProfileEntity();
        stub_saveProfileSettings(accountProfileDTO, accountProfileEntity);

        AccountProfileEntity result = accountProfileService.saveProfileSettings(accountProfileDTO);
        assertThat(result).isEqualTo(accountProfileEntity);
    }

    void stub_saveProfileSettings(AccountProfileDTO accountProfileDTO, AccountProfileEntity accountProfileEntity) {
        when(accountProfileMapper.to(eq(accountProfileDTO))).thenReturn(accountProfileEntity);
        when(accountProfileRepository.saveAndFlush(eq(accountProfileEntity))).thenReturn(accountProfileEntity);
    }
}