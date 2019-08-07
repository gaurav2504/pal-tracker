package io.pivotal.pal.tracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {

    public Map<Long, TimeEntry> timesheetRepository = new HashMap<Long, TimeEntry>();
    public Long timesheetId = 0L;

    public TimeEntry create(TimeEntry timeEntry) {
        timesheetId++;
        timeEntry.setId(timesheetId);
        timesheetRepository.put(timesheetId, timeEntry);
        return timeEntry;
    }

    public TimeEntry update(long l, TimeEntry timeEntry) {
        TimeEntry timeEntryLocal = timesheetRepository.get(l);
        if (timeEntryLocal != null) {
            timeEntry.setId(l);
            timesheetRepository.put(l, timeEntry);
            return timeEntry;
        } else {
            return null;
        }
    }

    public TimeEntry find(long id) {
        return timesheetRepository.get(id);
    }

    public TimeEntry delete(long id) {
        timesheetRepository.remove(id);
        return null;
    }

    @Override
    public void delete(Long timeEntryId) {

    }

    public List list() {
        List<TimeEntry> list = new ArrayList<TimeEntry>(timesheetRepository.values());
        return list;
    }
}
