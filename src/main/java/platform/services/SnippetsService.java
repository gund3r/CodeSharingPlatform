package platform.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import platform.repository.SnippetsRepository;
import platform.entities.Code;
import platform.exceptions.CodeNotFoundException;
import platform.utils.FormatDataTime;

import java.time.LocalTime;
import java.util.*;


@Service
public class SnippetsService {

    @Autowired
    SnippetsRepository repository;

    public String addCode(Code code) {
        System.out.println("addCode");
        Code newCode = new Code();
        newCode.setCode(code.getCode());
        newCode.setLocalDateTime();
        newCode.setDate(FormatDataTime.getFormatterDateTime());
        newCode.setTime(code.getTime());
        newCode.setViews(code.getViews());
        newCode.setTimeLimit(code.getTime() > 0);
        newCode.setViewsLimit(code.getViews() > 0);
        newCode.setUuid(UUID.randomUUID());
        UUID result = newCode.getUuid();
        System.out.println(newCode.toString());
        repository.save(newCode);;
        return "{ \"id\" : \"" + result + "\" }";
    }

    public Code[] getLatestCode() {
        System.out.println("getLatestCode");
        Arrays.toString(repository.findAllByTimeAndViewsOrderByDate().toArray(new Code[0]));
        return repository.findAllByTimeAndViewsOrderByDate().toArray(new Code[0]);
    }

    public Code getCodeFromRepository(UUID uuid) {
        System.out.println("getCodeFromRepository");
        if (repository.findById(uuid).isPresent()) {
            System.out.println("platform.entities.Code from repo: " + repository.findById(uuid).get().toString());
            return repository.findById(uuid).get();
        } else {
            throw new CodeNotFoundException();
        }
    }

    void deleteCodeFromRepository(Code code) {
        repository.delete(code);
    }

    public void updateTimeById(UUID uuid) {
        System.out.println("updateTimeById");
        Code codeToUpdate = getCodeFromRepository(uuid);
        int time = codeToUpdate.getTime();
        codeToUpdate.setTime(setTimeToSecretCode(codeToUpdate));
        System.out.println("platform.entities.Code was updated, now time is " + codeToUpdate.getTime());
        if (codeToUpdate.getTime() > 0) {
            repository.save(codeToUpdate);
        } else {
            repository.delete(codeToUpdate);
        }
    }

    public void updateViewsById(UUID uuid) {
        System.out.println("updateViewsById");
        Code codeToUpdate = getCodeFromRepository(uuid);
        int views = codeToUpdate.getViews();
        views--;
        codeToUpdate.setViews(views);
        if (codeToUpdate.getViews() >= 0) {
            repository.save(codeToUpdate);
        } else {
            repository.delete(codeToUpdate);
        }
        System.out.println("platform.entities.Code was updated, now views is " + codeToUpdate.getViews());
    }

    boolean isViewsOver(Code code) {
        System.out.println("isViewsOver");
        int viewsToWatch = code.getViews();
        System.out.println("Check for views, views is " + viewsToWatch);
        return viewsToWatch < 0 ? true : false;
    }

    boolean isTimeOver(Code code) {
        System.out.println("isTimeOver");
        int timeToWatch = code.getTime();
        System.out.println("Check time, time is " + timeToWatch);
        LocalTime timeOfCreation = getTimeOfCreation(code);
        System.out.println("TimeOfCreation is " + timeOfCreation);
        LocalTime localTime = LocalTime.now();
        System.out.println("LocalTime is " + localTime);
        int differenceOfTime = localTime.toSecondOfDay() - timeOfCreation.toSecondOfDay();;
        System.out.println("Difference of time is " + differenceOfTime);
        return differenceOfTime > timeToWatch ? true : false;
    }

    LocalTime getTimeOfCreation(Code code) {
        System.out.println("getTimeOfCreation");
        return code.getLocalDateTime().toLocalTime();
    }

    Integer setTimeToSecretCode(Code code) {
        System.out.println("setTimeToSecretCode");
        int result = 0;
        int timeToWatch = code.getTime();
        LocalTime timeOfCreation = getTimeOfCreation(code);
        LocalTime localTime = LocalTime.now();
        int difference = localTime.toSecondOfDay() - timeOfCreation.toSecondOfDay();
        if (difference > 0) {
            result = timeToWatch - difference;
        }
        return result;
    }

}
