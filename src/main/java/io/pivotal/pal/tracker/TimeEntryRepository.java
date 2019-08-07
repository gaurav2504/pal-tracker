package io.pivotal.pal.tracker;

import java.util.List;

public interface TimeEntryRepository {
    public TimeEntry create(TimeEntry timeEntryToCreate);

    public TimeEntry find(long timeEntryId);

    public List list();

    public TimeEntry update(long eq, TimeEntry any);

    public TimeEntry delete(long timeEntryId);

    public void delete(Long timeEntryId);
}
