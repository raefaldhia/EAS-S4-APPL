package id.ac.polban.jtk._2018.eas.appl._1;

import java.util.ArrayList;
import java.util.List;

public class ResourceManager {
    private final Library library;
    private final List<Resource> resources;

    ResourceManager (final Library library) {
        this.library = library;
        this.resources = new ArrayList<Resource>();
    }

    public Integer add (final String name, Resource.Type type) {
        Resource resource = null;
        switch (type) {
            case BOOK:
                resource = new Book(name);
                break;
            case COURSEPACK:
                resource = new CoursePack(name);
                break;
            case MAGAZINE:
                resource = new Magazine(name);
                break;
            default:
                throw new IllegalArgumentException("Unknown resource type");
        }

        resources.add(resource);

        return resource.getId();
    }

    public Resource get (final Integer id) {
        for (Resource resource : resources) {
            if (resource.getId().equals(id)) {
                return resource;
            }
        }

        return null;
    }

    public List<Resource> getByName (final String name) {
        List<Resource> result = new ArrayList<>();

        for (Resource resource : resources) {
            if (resource.getId().equals(name)) {
                result.add(resource);
            }
        }

        return result;
    }

    public Boolean remove (final Integer id) {
        for (int i = 0; i < resources.size(); ++i) {
            if (resources.get(i).getId().equals(id)) {
                if (!(resources.get(i) instanceof Magazine)) {
                    Borrowable bor = (Borrowable)resources;
                    for(int j=0;j<bor.requests.size();j++){
                        ((Borrower)library.getUserManager().get(bor.requests.get(i))).deleteRequest(bor.getId());
                    }
                    if(bor.checkStatus()){
                        Borrower borrower = (Borrower)library.getUserManager().get(bor.issuedTo);
                        borrower.tryReturn(bor.getId());
                    }        
                }
                resources.remove(i);

                return true;
            }
        }

        return false;
    }

    public List<Resource> getResources() {
        return resources;
    }
}