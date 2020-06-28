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
        if (r.uuid != null && Arrays.stream(storage).filter(Objects::nonNull).noneMatch(resume -> resume.uuid.equals(r.uuid))) {
            storage[counter++] = r;
        }
    }

    Resume get(String uuid) {
        for (int i = 0; i < counter; i++) {
            if (storage[i].uuid.equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        int index = 0;
        for (int i = 0; i < counter; i++) {
            if (!(storage[i].uuid.equals(uuid))) {
                index++;
            } else {
                for (int j = index; j < counter - 1; j++) {
                    storage[j] = storage[j + 1];
                }
                storage[--counter] = null;
                return;
            }
        }
    }

    Resume[] getAll() {
        return Arrays.stream(storage).filter(Objects::nonNull).toArray(Resume[]::new);
    }

    int size() {
        return counter;
    }
}
