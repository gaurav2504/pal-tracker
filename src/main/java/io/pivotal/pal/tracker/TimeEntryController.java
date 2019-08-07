package io.pivotal.pal.tracker;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
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
    private final DistributionSummary timeEntrySummary;
    private final Counter actionCounter;

    public TimeEntryController(TimeEntryRepository timeEntriesRepo, MeterRegistry meterRegistry) {
        this.timeEntryRepository = timeEntriesRepo;
        timeEntrySummary = meterRegistry.summary("timeEntry.summary");
        actionCounter = meterRegistry.counter("timeEntry.actionCounter");
    }

    @PostMapping
    public ResponseEntity create(@RequestBody TimeEntry timeEntryToCreate) {
        TimeEntry newTimeEntry=timeEntryRepository.create(timeEntryToCreate);
        actionCounter.increment();
        timeEntrySummary.record(timeEntryRepository.list().size());

        return new ResponseEntity(newTimeEntry, HttpStatus.CREATED);
    }
    @GetMapping("{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable long id) {
        TimeEntry newTimeEntry=timeEntryRepository.find(id);
        if(newTimeEntry!=null) {
            actionCounter.increment();
            return new ResponseEntity(newTimeEntry, HttpStatus.OK);
        }else{
            return new ResponseEntity(newTimeEntry, HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping
    public ResponseEntity<List<TimeEntry>> list() {
        actionCounter.increment();
        List<TimeEntry> list = new ArrayList<TimeEntry>(timeEntryRepository.list());
        return new ResponseEntity(list, HttpStatus.OK);
    }
    @PutMapping("{id}")
    public ResponseEntity update(@PathVariable long id, @RequestBody TimeEntry timeEntryToUpdate ) {
        TimeEntry newTimeEntry=timeEntryRepository.update(id,timeEntryToUpdate );
        actionCounter.increment();
        if(newTimeEntry!=null) {
            return new ResponseEntity(newTimeEntry, HttpStatus.OK);
        }else{
            return new ResponseEntity(newTimeEntry, HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable long id) {
        TimeEntry newTimeEntry=timeEntryRepository.delete(id);
        actionCounter.increment();
        timeEntrySummary.record(timeEntryRepository.list().size());
        return new ResponseEntity(newTimeEntry, HttpStatus.NO_CONTENT);
    }
}
