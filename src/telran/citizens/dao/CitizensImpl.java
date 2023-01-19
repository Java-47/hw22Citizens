package telran.citizens.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import telran.citizens.interfaces.Citizens;
import telran.citizens.model.Person;

public class CitizensImpl implements Citizens {
	private List<Person> idList;
	private List<Person> lastNameList;
	private List<Person> ageList;
	private static Comparator<Person> lastNameComparator;
	private static Comparator<Person> ageComparator;

	public CitizensImpl() {
		lastNameComparator = new lastNameComparator();
		ageComparator = new ageComparator();

		idList = new ArrayList<>();
		lastNameList = new ArrayList<>();
		ageList = new ArrayList<>();

	}

//O(n*log(n))
	public CitizensImpl(List<Person> citizens) {
		lastNameComparator = new lastNameComparator();
		ageComparator = new ageComparator();

		idList = new ArrayList<Person>();
		ageList = new ArrayList<Person>();
		lastNameList = new ArrayList<Person>();

		for (Person person : citizens) {
			add(person);
		}
		lastNameList.sort(lastNameComparator);
		ageList.sort(ageComparator);
	}

	class lastNameComparator implements Comparator<Person> {
		@Override
		public int compare(Person p1, Person p2) {
			return p1.getLastName().compareTo(p2.getLastName());
		}
	}

	class ageComparator implements Comparator<Person> {
		@Override
		public int compare(Person p1, Person p2) {
			if (p1.getAge() != p2.getAge()) {
				return p1.getAge() - p2.getAge();
			}
			return p1.getAge() - p2.getAge();
		}
	}

	@Override
	// O(log(n))
	public boolean add(Person person) {
		if (person == null || find(person.getId()) != null) {
			return false;
		}
		//idList
	    int idListIndex = Collections.binarySearch(idList, person);
	    idListIndex = idListIndex < 0 ? -(idListIndex + 1) : idListIndex;
	    idList.add(idListIndex, person);
	    //LastNameList
	    int lastNameIndex = Collections.binarySearch(lastNameList, person, lastNameComparator);
	    lastNameIndex = lastNameIndex < 0 ? -(lastNameIndex + 1) : lastNameIndex;
	    lastNameList.add(lastNameIndex, person);
	    //ageList
	    int ageIndex = Collections.binarySearch(ageList, person, ageComparator);
	    ageIndex = ageIndex < 0 ? -(ageIndex + 1) : ageIndex;
	    ageList.add(ageIndex, person);
	   
	    return true;

	}

	// O(n)
	@Override
	public boolean remove(int id) {
		if (find(id) == null) {
			return false;
		}
		idList.remove(find(id));
		lastNameList.remove(find(id));
		ageList.remove(find(id));
		return true;
	}

	// O(log(n))
	@Override
	public Person find(int id) {

		List<Person> personsId = new ArrayList<Person>(idList);
		Person pattern = new Person(id, null, null, 0);

		int index = Collections.binarySearch(personsId, pattern);
		if (index < 0) {
			return null;
		}

		return personsId.get(index);
	}

	// O(log(n))
	@Override
	public Iterable<Person> find(int minAge, int maxAge) {
		int from = 0;
		int to = 0;
		
		Person pattern = new Person(0, null, null, minAge);
		from = Collections.binarySearch(ageList, pattern, ageComparator);
		pattern = new Person(0, null, null, maxAge);
		to = Collections.binarySearch(ageList, pattern, ageComparator);
	    

		return ageList.subList(from-1, to+1);
	}

	// O(n)
	@Override
	public Iterable<Person> find(String lastName) {
		int from = 0;
		int to = 0;
		int index = 0;
		boolean flag = true;
		List<Person> lastNameNewList = new ArrayList<Person>(lastNameList);

		for (Person person : lastNameNewList) {
			if (person.getLastName().equals(lastName)) {
				if (flag) {
					from = index;
					flag = false;
				}
			}
			if (person.getLastName().equals(lastName)) {
				to = index + 1;
			}
			index++;
		}

		lastNameNewList = lastNameNewList.subList(from, to);
		return lastNameNewList;
		
	}

	@Override
	public Iterable<Person> getAllPersonSortedById() {
		return idList;
	}

	@Override
	public Iterable<Person> getAllPersonSortedByLastName() {
		return lastNameList;
	}

	@Override
	public Iterable<Person> getAllPersonSortedByAge() {
		return ageList;
	}

	@Override
	public int size() {
		return idList.size();
	}

}
