package ru.magicvolley.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.magicvolley.Util;
import ru.magicvolley.botTelegram.Bot;
import ru.magicvolley.dto.AnswerDto;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final Bot bot;

    @Transactional
    public Boolean makeAnswer(AnswerDto answerDto) {

        String answer = answerDto.answer();
        String userName = answerDto.userName();
        String telephone = Util.addNotExistChar(answerDto.telephone().trim(), '+');
        bot.sendAnswer(answer, userName, telephone);
        return true;
    }
}
