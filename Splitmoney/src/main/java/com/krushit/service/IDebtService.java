package com.krushit.service;

import java.util.List;
import java.util.Optional;

import com.krushit.model.DebtModel;

public interface IDebtService {

    /**
     * Creates debts based on the expenses associated with the specified group ID.
     *
     * @param groupId The ID of the group for which to create debts.
     */
    void createDebt(Long groupId);

    /**
     * Updates an existing debt with the information from the provided DebtModel.
     *
     * @param debtModel The DebtModel containing the updated debt information.
     * @return The updated DebtModel, or null if the debt was not found.
     */
    DebtModel updateDebt(DebtModel debtModel);

    /**
     * Retrieves a debt by its ID.
     *
     * @param id The ID of the debt to retrieve.
     * @return An Optional containing the DebtModel if found, or empty if not found.
     */
    Optional<DebtModel> getDebtById(Long id);

    /**
     * Deletes a debt by its ID.
     *
     * @param id The ID of the debt to delete.
     */
    void deleteDebt(Long id);

    /**
     * Retrieves all debts.
     *
     * @return A list of DebtModel representing all debts.
     */
    List<DebtModel> getAllDebts();
}
