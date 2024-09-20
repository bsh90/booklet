package library.booklet.service.account.setting;

import library.booklet.dto.AccountProfileDTO;
import library.booklet.entity.AccountProfileEntity;
import library.booklet.mapper.AccountProfileMapper;
import library.booklet.repository.AccountProfileRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class AccountProfileService {

    @Autowired
    AccountProfileRepository accountProfileRepository;

    @Autowired
    AccountProfileMapper accountProfileMapper;

    public AccountProfileEntity saveProfileSettings(AccountProfileDTO accountProfileDTO) {
        AccountProfileEntity accountProfileEntity = accountProfileMapper.to(accountProfileDTO);
        return accountProfileRepository.saveAndFlush(accountProfileEntity);
    }
}
