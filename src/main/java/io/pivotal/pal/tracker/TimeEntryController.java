package io.pivotal.pal.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {

    TimeEntryRepository timeEntryRepository;
    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        this.timeEntryRepository = timeEntryRepository;
    }
    @PostMapping
    public ResponseEntity create(@RequestBody TimeEntry timeEntryToCreate) {
        TimeEntry newTimeEntry=timeEntryRepository.create(timeEntryToCreate);
        return new ResponseEntity(newTimeEntry, HttpStatus.CREATED);
    }
    @GetMapping("{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable long id) {
        TimeEntry newTimeEntry=timeEntryRepository.find(id);
        if(newTimeEntry!=null) {
            return new ResponseEntity(newTimeEntry, HttpStatus.OK);
        }else{
            return new ResponseEntity(newTimeEntry, HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping
    public ResponseEntity<List<TimeEntry>> list() {
       // TimeEntry newTimeEntry=timeEntryRepository.list();
        List<TimeEntry> list = new ArrayList<TimeEntry>(timeEntryRepository.list());
        return new ResponseEntity(list, HttpStatus.OK);
    }
    @PutMapping("{id}")
    public ResponseEntity update(@PathVariable long id, @RequestBody TimeEntry timeEntryToUpdate ) {
        TimeEntry newTimeEntry=timeEntryRepository.update(id,timeEntryToUpdate );
        if(newTimeEntry!=null) {
            return new ResponseEntity(newTimeEntry, HttpStatus.OK);
        }else{
            return new ResponseEntity(newTimeEntry, HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable long id) {
        TimeEntry newTimeEntry=timeEntryRepository.delete(id);
        return new ResponseEntity(newTimeEntry, HttpStatus.NO_CONTENT);
    }
}
