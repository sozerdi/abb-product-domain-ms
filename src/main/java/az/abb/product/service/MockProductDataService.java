package az.abb.product.service;

import az.abb.product.dto.offlineproduct.AccountData;
import az.abb.product.dto.offlineproduct.CardData;
import az.abb.product.dto.offlineproduct.IdentifierOptions;
import az.abb.product.dto.offlineproduct.ProductDto;
import az.abb.product.dto.offlineproduct.ProductType;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@lombok.RequiredArgsConstructor
public class MockProductDataService {
    // YAML mapping removed; JSON-driven repository used instead
    private final az.abb.product.config.MockDataRepository mockDataRepository;
    
    // Mock data constants (kept for backward compatibility if needed)
    // All mock data is now JSON-driven
    
    public ProductDto<CardData> createMockCard() {
        // Use JSON-driven data, fallback to first available card
        var jsonSeeds = mockDataRepository.getCards();
        if (jsonSeeds != null && !jsonSeeds.isEmpty()) {
            var firstCard = jsonSeeds.get(0);
            return toProductDtoFromSeed(SimpleCardSeed.of(
                    firstCard.getNumber(),
                    firstCard.getAlias(),
                    firstCard.getCif(),
                    firstCard.getContractNumber(),
                    firstCard.getCardNetwork(),
                    firstCard.getDescription(),
                    firstCard.getCurrency(),
                    firstCard.getExpiryDate(),
                    firstCard.getCardStatus(),
                    firstCard.getCardType(),
                    firstCard.getPinEraseEligible(),
                    firstCard.getAccountClass(),
                    firstCard.getAccountNumber(),
                    firstCard.getEligibleForAvans(),
                    firstCard.getUsageType(),
                    firstCard.getUserId(),
                    firstCard.getPin(),
                    firstCard.getProductCode1(),
                    firstCard.getAvailableBalance()
            ), 1);
        }
        
        // Fallback to empty result if no JSON data available
        return null;
    }
    
    public ProductDto<AccountData> createMockAccount() {
        // Use JSON-driven data, fallback to first available account
        var jsonSeeds = mockDataRepository.getAccounts();
        if (jsonSeeds != null && !jsonSeeds.isEmpty()) {
            var firstAccount = jsonSeeds.get(0);
            
            AccountData.Customer customer = new AccountData.Customer();
            customer.setName(null);
            customer.setCif(firstAccount.getCif());
            customer.setPassportId(null);

            AccountData.AccountDetails details = new AccountData.AccountDetails();
            details.setNumber(firstAccount.getAccountNumber());
            details.setCategory(null);
            details.setBookAccount(firstAccount.getAccountNumber());
            details.setIban(firstAccount.getIban());
            details.setAlternativeNumber(firstAccount.getAccountNumber());

            AccountData account = new AccountData();
            account.setDescription(firstAccount.getDescription());
            account.setCustomer(customer);
            account.setAccount(details);
            account.setBranch(null);
            account.setBalance(null);
            account.setLcyCurrBalance(null);
            account.setAcyCurrBalance(null);
            account.setCurrency(firstAccount.getCurrency());
            account.setStatus(firstAccount.getStatus());
            account.setAccountClass(firstAccount.getAccountClass());

            ProductDto<AccountData> dto = new ProductDto<>();
            dto.setId(firstAccount.getAccountNumber());
            dto.setType(ProductType.ACCOUNT);
            dto.setAlias("Account");
            dto.setVisible(true);
            dto.setPosition(1);
            dto.setUserId(firstAccount.getUserId());
            dto.setCreatedAt(Instant.now());
            dto.setUpdatedAt(Instant.now());
            dto.setDeletedAt(null);
            dto.setData(account);
            
            return dto;
        }
        
        // Fallback to empty result if no JSON data available
        return null;
    }

    // --- Bulk mock generation from provided sample dataset (curated) ---

    public List<ProductDto<CardData>> createMockCardsBulk() {
        return createMockCardsBulk(null);
    }

    public List<ProductDto<CardData>> createMockCardsBulk(IdentifierOptions opts) {
        List<SimpleCardSeed> seeds = new ArrayList<>();

        // Use JSON-driven seeds
        var jsonSeeds = mockDataRepository.getCards();
        if (jsonSeeds != null && !jsonSeeds.isEmpty()) {
            for (var js : jsonSeeds) {
                seeds.add(SimpleCardSeed.of(
                        js.getNumber(),
                        js.getAlias(),
                        js.getCif(),
                        js.getContractNumber(),
                        js.getCardNetwork(),
                        js.getDescription(),
                        js.getCurrency(),
                        js.getExpiryDate(),
                        js.getCardStatus(),
                        js.getCardType(),
                        js.getPinEraseEligible(),
                        js.getAccountClass(),
                        js.getAccountNumber(),
                        js.getEligibleForAvans(),
                        js.getUsageType(),
                        js.getUserId(),
                        js.getPin(),
                        js.getProductCode1(),
                        js.getAvailableBalance()
                ));
            }
        }

        List<ProductDto<CardData>> result = new ArrayList<>();
        for (int i = 0; i < seeds.size(); i++) {
            result.add(toProductDtoFromSeed(seeds.get(i), i + 1));
        }
        return result;
    }

    public ProductDto<CardData> findMockCardByNumberOrId(String numberOrId) {
        if (StringUtils.isBlank(numberOrId)) {
            return null;
        }
        return createMockCardsBulk().stream()
                .filter(p -> Objects.equals(p.getId(), numberOrId)
                        || (p.getData() != null 
                            && Objects.equals(((CardData) p.getData()).getNumber(), numberOrId)))
                .findFirst()
                .orElse(null);
    }

    public List<ProductDto<CardData>> filterCardsByIdentifierOptions(
            List<ProductDto<CardData>> cards, IdentifierOptions opts) {
        return cards.stream()
                .filter(Objects::nonNull)
                // Filter by userId if provided
                .filter(p -> opts == null || opts.getUserId() == null 
                        || Objects.equals(p.getUserId(), opts.getUserId()))
                // Filter by CIFs if provided
                .filter(p -> {
                    if (opts == null || opts.getCifs() == null || opts.getCifs().isEmpty()) {
                        return true;
                    }
                    if (p.getData() == null) {
                        return false;
                    }
                    CardData cd = (CardData) p.getData();
                    return cd.getCustomer() != null && opts.getCifs().contains(cd.getCustomer().getCif());
                })
                // Filter by PIN if provided
                .filter(p -> {
                    if (opts == null || opts.getPin() == null || opts.getPin().isEmpty()) {
                        return true;
                    }
                    // Find the corresponding card seed to get PIN
                    var jsonSeeds = mockDataRepository.getCards();
                    if (jsonSeeds == null) {
                        return false;
                    }
                    return jsonSeeds.stream()
                            .anyMatch(seed -> {
                                if (p.getData() == null) {
                                    return false;
                                }
                                CardData cd = (CardData) p.getData();
                                return Objects.equals(seed.getNumber(), cd.getNumber()) 
                                        && Objects.equals(seed.getPin(), opts.getPin());
                            });
                })
                .toList();
    }

    private static ProductDto<CardData> toProductDtoFromSeed(SimpleCardSeed s, int position) {
        CardData.Customer customer = CardData.Customer.builder()
                .name(null)
                .cif(s.cif)
                .build();

        CardData.Account.Branch branch = CardData.Account.Branch.builder()
                .code(s.accountBranchCode)
                .build();

        CardData.Account account = CardData.Account.builder()
                .number(s.accountNumber)
                .accountClass(s.accountClass)
                .name(s.description)
                .branch(branch)
                .build();

        CardData.Product product = CardData.Product.builder()
                .productName(s.description)
                .productCode1(s.productCode1)
                .build();

        CardData card = new CardData();
        card.setNumber(s.number);
        card.setExpiryDate(s.expiryDate);
        card.setExpired(Boolean.FALSE);
        card.setNetwork(s.cardNetwork);
        card.setCardType(s.cardType);
        card.setUsageType(s.usageType);
        card.setStatus(s.cardStatus);
        card.setCurrency(s.currency);
        card.setCreditLimit(BigDecimal.ZERO);
        card.setBalance(BigDecimal.ZERO);
        card.setAvailableBalance(s.availableBalance != null ? BigDecimal.valueOf(s.availableBalance) : BigDecimal.ZERO);
        card.setCustomer(customer);
        card.setAccount(account);
        card.setProduct(product);
        card.setContract(CardData.Contract.builder().number(s.contractNumber).build());
        card.setFeatures(CardData.Features.builder()
                .pinEraseEligible(Boolean.TRUE.equals(s.isPinEraseEligible))
                .avansEligible(Boolean.TRUE.equals(s.isEligibleForAvans))
                .build());

        ProductDto<CardData> dto = new ProductDto<>();
        dto.setId(s.contractNumber != null ? s.contractNumber : s.number);
        dto.setType(ProductType.CARD);
        dto.setAlias(s.alias);
        dto.setVisible(true);
        dto.setPosition(position);
        dto.setUserId(s.userId != null ? s.userId : java.util.UUID.fromString("00000000-0000-0000-0000-000000000000"));
        dto.setCreatedAt(Instant.now());
        dto.setUpdatedAt(Instant.now());
        dto.setDeletedAt(null);
        dto.setData(card);
        return dto;
    }

    // Accounts from JSON repository
    public List<ProductDto<AccountData>> createMockAccountsBulk() {
        List<ProductDto<AccountData>> result = new ArrayList<>();
        var seeds = mockDataRepository.getAccounts();
        if (seeds == null || seeds.isEmpty()) {
            return result;
        }
        for (int i = 0; i < seeds.size(); i++) {
            var s = seeds.get(i);
            AccountData.Customer customer = new AccountData.Customer();
            customer.setName(null);
            customer.setCif(s.getCif());
            customer.setPassportId(null);

            AccountData.AccountDetails details = new AccountData.AccountDetails();
            details.setNumber(s.getAccountNumber());
            details.setCategory(null);
            details.setBookAccount(s.getAccountNumber());
            details.setIban(s.getIban());
            details.setAlternativeNumber(s.getAccountNumber());

            AccountData account = new AccountData();
            account.setDescription(s.getDescription());
            account.setCustomer(customer);
            account.setAccount(details);
            account.setBranch(null);
            account.setBalance(null);
            account.setLcyCurrBalance(null);
            account.setAcyCurrBalance(null);
            account.setCurrency(s.getCurrency());
            account.setStatus(s.getStatus());
            account.setAccountClass(s.getAccountClass());

            ProductDto<AccountData> dto = new ProductDto<>();
            dto.setId(s.getAccountNumber());
            dto.setType(ProductType.ACCOUNT);
            dto.setAlias("Account");
            dto.setVisible(true);
            dto.setPosition(i + 1);
            dto.setUserId(s.getUserId());
            dto.setCreatedAt(Instant.now());
            dto.setUpdatedAt(Instant.now());
            dto.setDeletedAt(null);
            dto.setData(account);

            result.add(dto);
        }
        return result;
    }

    public List<ProductDto<AccountData>> filterAccountsByIdentifierOptions(
            List<ProductDto<AccountData>> accounts, IdentifierOptions opts) {
        return accounts.stream()
                .filter(Objects::nonNull)
                .filter(p -> opts == null || opts.getUserId() == null 
                        || Objects.equals(p.getUserId(), opts.getUserId()))
                .filter(p -> {
                    if (opts == null || opts.getCifs() == null || opts.getCifs().isEmpty()) {
                        return true;
                    }
                    if (p.getData() == null) {
                        return false;
                    }
                    AccountData ad = (AccountData) p.getData();
                    return ad.getCustomer() != null && opts.getCifs().contains(ad.getCustomer().getCif());
                })
                // Filter by PIN if provided
                .filter(p -> {
                    if (opts == null || opts.getPin() == null || opts.getPin().isEmpty()) {
                        return true;
                    }
                    // Find the corresponding account seed to get PIN
                    var jsonSeeds = mockDataRepository.getAccounts();
                    if (jsonSeeds == null) {
                        return false;
                    }
                    return jsonSeeds.stream()
                            .anyMatch(seed -> {
                                if (p.getData() == null) {
                                    return false;
                                }
                                AccountData ad = (AccountData) p.getData();
                                return Objects.equals(seed.getAccountNumber(), ad.getAccount().getNumber()) 
                                        && Objects.equals(seed.getPin(), opts.getPin());
                            });
                })
                .toList();
    }



    @Data
    @Builder
    @AllArgsConstructor
    private static class SimpleCardSeed {
        private String number;
        private String alias;
        private String cif;
        private String contractNumber;
        private String cardNetwork;
        private String description;
        private String currency;
        private String expiryDate;
        private String cardStatus;
        private String cardType;
        private Boolean isPinEraseEligible;
        private String accountClass;
        private String accountNumber;
        private Boolean isEligibleForAvans;
        private String usageType;
        private java.util.UUID userId;
        private String pin;
        private String productCode1;
        private Double availableBalance;
        
        // Optional extra
        private String accountBranchCode;
        
        public static SimpleCardSeed of(String number, String alias, String cif, String contractNumber,
                                        String cardNetwork, String description, String currency, String expiryDate,
                                        String cardStatus, String productTypeOrCardType, Boolean pinEraseEligible,
                                        String accountClass, String accountNumber, Boolean eligibleForAvans,
                                        String usageType, java.util.UUID userId, String pin, String productCode1,
                                        Double availableBalance) {
            return SimpleCardSeed.builder()
                    .number(number)
                    .alias(alias)
                    .cif(cif)
                    .contractNumber(contractNumber)
                    .cardNetwork(cardNetwork)
                    .description(description)
                    .currency(currency)
                    .expiryDate(expiryDate)
                    .cardStatus(cardStatus)
                    .cardType(productTypeOrCardType)
                    .isPinEraseEligible(pinEraseEligible)
                    .accountClass(accountClass)
                    .accountNumber(accountNumber)
                    .isEligibleForAvans(eligibleForAvans)
                    .usageType(usageType)
                    .accountBranchCode(null)
                    .userId(userId)
                    .pin(pin)
                    .productCode1(productCode1)
                    .availableBalance(availableBalance)
                    .build();
        }
    }
}
