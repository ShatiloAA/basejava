import java.util.Arrays;
import java.util.Objects;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    private int counter = 0;

    void clear() {
        for (int i = 0; i < counter; i++) {
            storage[i] = null;
        }
        counter = 0;
    }

    void save(Resume r) {
        if (Arrays.stream(storage).filter(Objects::nonNull).noneMatch(resume -> resume.uuid.equals(r.uuid))) {
            storage[counter++] = r;
        }
    }

    Resume get(String uuid) {
        for (Resume resume : storage) {
            if (resume != null && resume.uuid.equals(uuid)) {
                return resume;
            }
        }
        return null;
    }

    void delete(String uuid) {

        int indexDelObj = 0;

        for (Resume r : storage) {
            if (!(r.uuid.equals(uuid))) {
                indexDelObj++;
            } else {
                for (int i = indexDelObj; i < counter; i++) {
                    storage[i] = storage[i + 1];
                }

                return;
            }

        }
        storage[--counter] = null;
    }

    Resume[] getAll() {
        return Arrays.stream(storage).filter(Objects::nonNull).toArray(Resume[]::new);
    }

    int size() {
        return counter;
    }
}
