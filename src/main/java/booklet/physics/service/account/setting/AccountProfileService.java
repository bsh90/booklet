package booklet.physics.service.account.setting;

import booklet.physics.dto.AccountProfileDTO;
import booklet.physics.entity.AccountProfileEntity;
import booklet.physics.mapper.AccountProfileMapper;
import booklet.physics.repository.AccountProfileRepository;
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

    public void saveProfileSettings(AccountProfileDTO accountProfileDTO) {
        AccountProfileEntity accountProfileEntity = accountProfileMapper.to(accountProfileDTO);
        accountProfileRepository.saveAndFlush(accountProfileEntity);
    }
}
