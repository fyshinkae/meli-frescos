package com.example.mercadofrescos.service;

import com.example.mercadofrescos.model.Address;
import com.example.mercadofrescos.repository.IAddressRepo;
import com.example.mercadofrescos.service.interfaces.IAdressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressService implements IAdressService {

    private final IAddressRepo repo;

    @Override
    public Address findAdressById(Long id) {
        return repo.findById(id).get();
    }

    @Override
    public Address createAdress(Address address) {
        return repo.save(address);
    }

    @Override
    public Address updateAdress(Address address) {
        Optional<Address> addressToUpdate = repo.findById(address.getId());

        if (addressToUpdate.isEmpty()) {
            throw new EntityNotFoundException("Address not found");
        }
        return repo.save(address);
    }

    @Override
    public void deleteAdress(Long id) {
        Optional<Address> addressToDelete = repo.findById(id);

        if (!addressToDelete.isEmpty()) {
            throw new EntityNotFoundException("Address not found");
        }
        repo.delete(addressToDelete.get());
    }
}
