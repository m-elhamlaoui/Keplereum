package com.example.jeeHamlaoui.Blockchain_Service.config;

import com.example.jeeHamlaoui.Blockchain_Service.model.NetworkNode;
import com.example.jeeHamlaoui.Blockchain_Service.repository.NetworkNodeRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class StartupDataLoader {

    @Autowired
    private NetworkNodeRepository networkNodeRepository;

    @PostConstruct
    public void loadInitialData() {
        // Check if data already exists
        try {
            if (networkNodeRepository.count() == 0) {
                networkNodeRepository.save(new NetworkNode(
                        "0xe6427a00f96f7ca5688f1de97ba66c21daf77afe",
                        "0xef43f9f547e1a70532ba05824bda6e90d58f1d8c44aeb75ec1354839ad4b7738",
                        "AURA",
                        true,
                        234,
                        Instant.now()
                ));

                networkNodeRepository.save(new NetworkNode(
                        "0x8ae4132bc3a5349245e5cb5df336f7bd1c312c44",
                        "0x9ebd7b9d4f23803e11ef2fe584fb2376d69c033ac9faa3380cc4ef03579f2288",
                        "AQUA",
                        true,
                        124,
                        Instant.now()
                ));

                networkNodeRepository.save(new NetworkNode(
                        "0x586c6a428dfa031a56360e634cd8d87ea38441c1",
                        "0xef542e7715b47b225e6a16f27e54027f6605158a1d90932acf89fe18f4040614",
                        "CALIPSO",
                        true,
                        78,
                        Instant.now()
                ));

                networkNodeRepository.save(new NetworkNode(
                        "0xe680c674b7ee03080c8c11099d499c68895088cb",
                        "0xc5b5e89df438017e7c9cba3b0dc6f544e46ac794d17c463ff553bf92c05939bb",
                        "GCOM-W1",
                        true,
                        122,
                        Instant.now()
                ));

                networkNodeRepository.save(new NetworkNode(
                        "0x90501ed9e1d4466512a09fe41157a1fad6ed76e7",
                        "0x355af7593acfc5d95a3c08811c3b1749ac2f9e8b17775b3394f5a28b07d61150",
                        "DELTA",
                        true,
                        200,
                        Instant.now()
                ));

                System.out.println("Initial data loaded into the database.");
            } else {
                System.out.println("Data already exists. Skipping initial data load.");
            }
        } catch (Exception e) {
            System.err.println("Error Injecting Initial Data: " + e.getMessage());
        }
    }
}