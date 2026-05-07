package edu.itmo.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Доменная модель: кит, несвоё положение, осознание и потеря «китовости»")
class DomainModelTest {

    @Test
    @DisplayName("Начальное состояние: дезориентация, обычная среда, время ещё есть")
    void initialState() {
        WhaleSubject w = new WhaleSubject();
        assertEquals(WhaleNarrativeState.DISORIENTED_WHALE, w.getNarrativeState());
        assertEquals(BioSocialPredicament.ORDINARY, w.getPredicament());
        assertEquals(TemporalResource.REMAINING, w.getTimeForWhaleSelfIntegration());
    }

    @Test
    @DisplayName("Без нелепой среды осознание себя китом не наступает")
    void realizationRequiresUnnaturalPredicament() {
        WhaleSubject w = new WhaleSubject();
        w.realizeCetaceanIdentity();
        assertEquals(WhaleNarrativeState.DISORIENTED_WHALE, w.getNarrativeState());
    }

    @Test
    @DisplayName("В нелепой среде возможен переход к интеграции образа кита")
    void unnaturalContextAllowsWhaleIdentity() {
        WhaleSubject w = new WhaleSubject();
        w.enterUnnaturalCircumstances();
        w.realizeCetaceanIdentity();
        assertEquals(BioSocialPredicament.UNNATURAL_FOR_WHALE_BODY, w.getPredicament());
        assertEquals(WhaleNarrativeState.WHALE_IDENTITY_INTEGRATING, w.getNarrativeState());
    }

    @Test
    @DisplayName("После исчерпания времени нельзя «впервые» осознать себя китом")
    void noRealizationAfterTimeExhaustedFromDisoriented() {
        WhaleSubject w = new WhaleSubject();
        w.enterUnnaturalCircumstances();
        w.elapseTimeForWhaleIntegration();
        w.realizeCetaceanIdentity();
        assertEquals(TemporalResource.EXHAUSTED, w.getTimeForWhaleSelfIntegration());
        assertEquals(WhaleNarrativeState.DISORIENTED_WHALE, w.getNarrativeState());
    }

    @Test
    @DisplayName("Исчерпание времени во время интеграции переводит к привыканию к потере китовости")
    void timeRunsOutDuringIntegration() {
        WhaleSubject w = new WhaleSubject();
        w.enterUnnaturalCircumstances();
        w.realizeCetaceanIdentity();
        w.elapseTimeForWhaleIntegration();
        assertEquals(WhaleNarrativeState.ADJUSTING_AFTER_LOSING_WHALEHOOD, w.getNarrativeState());
        assertEquals(TemporalResource.EXHAUSTED, w.getTimeForWhaleSelfIntegration());
    }

    @Test
    @DisplayName("Потеря китовости из дезориентации — сразу фаза readjustment")
    void lossOfWhalehoodFromDisoriented() {
        WhaleSubject w = new WhaleSubject();
        w.enterUnnaturalCircumstances();
        w.registerLossOfWhalehood();
        assertEquals(WhaleNarrativeState.ADJUSTING_AFTER_LOSING_WHALEHOOD, w.getNarrativeState());
    }

    @Test
    @DisplayName("Потеря китовости из состояния интеграции образа кита")
    void lossOfWhalehoodFromIntegrating() {
        WhaleSubject w = new WhaleSubject();
        w.enterUnnaturalCircumstances();
        w.realizeCetaceanIdentity();
        w.registerLossOfWhalehood();
        assertEquals(WhaleNarrativeState.ADJUSTING_AFTER_LOSING_WHALEHOOD, w.getNarrativeState());
    }

    @Test
    @DisplayName("Повторный вызов registerLossOfWhalehood идемпотентен в фазе readjustment")
    void repeatedLossRegistrationNoOp() {
        WhaleSubject w = new WhaleSubject();
        w.enterUnnaturalCircumstances();
        w.registerLossOfWhalehood();
        w.registerLossOfWhalehood();
        assertEquals(WhaleNarrativeState.ADJUSTING_AFTER_LOSING_WHALEHOOD, w.getNarrativeState());
    }

    @Test
    @DisplayName("Завершение привыкания после потери китовости")
    void completeAdjustment() {
        WhaleSubject w = new WhaleSubject();
        w.enterUnnaturalCircumstances();
        w.registerLossOfWhalehood();
        w.completePostWhaleAdjustment();
        assertEquals(WhaleNarrativeState.STABILIZED_AFTER_TRANSFORMATION, w.getNarrativeState());
    }

    @Test
    @DisplayName("completePostWhaleAdjustment не меняет состояние, если не в фазе readjustment")
    void completeAdjustmentWrongPhaseNoOp() {
        WhaleSubject w = new WhaleSubject();
        w.completePostWhaleAdjustment();
        assertEquals(WhaleNarrativeState.DISORIENTED_WHALE, w.getNarrativeState());
    }

    @Test
    @DisplayName("После стабилизации registerLossOfWhalehood не откатывает сценарий")
    void lossAfterStabilizedIgnored() {
        WhaleSubject w = new WhaleSubject();
        w.enterUnnaturalCircumstances();
        w.registerLossOfWhalehood();
        w.completePostWhaleAdjustment();
        w.registerLossOfWhalehood();
        assertEquals(WhaleNarrativeState.STABILIZED_AFTER_TRANSFORMATION, w.getNarrativeState());
    }

    @Test
    @DisplayName("Повторное осознание кита не выполняется при уже пройденной интеграции")
    void realizeAgainAfterIntegratingNoOp() {
        WhaleSubject w = new WhaleSubject();
        w.enterUnnaturalCircumstances();
        w.realizeCetaceanIdentity();
        w.realizeCetaceanIdentity();
        assertEquals(WhaleNarrativeState.WHALE_IDENTITY_INTEGRATING, w.getNarrativeState());
    }

    @Test
    @DisplayName("Исчерпание времени уже в фазе readjustment не меняет narrativeState")
    void elapseTimeWhenAlreadyAdjusting() {
        WhaleSubject w = new WhaleSubject();
        w.enterUnnaturalCircumstances();
        w.registerLossOfWhalehood();
        w.elapseTimeForWhaleIntegration();
        assertEquals(WhaleNarrativeState.ADJUSTING_AFTER_LOSING_WHALEHOOD, w.getNarrativeState());
        assertEquals(TemporalResource.EXHAUSTED, w.getTimeForWhaleSelfIntegration());
    }

    @Test
    @DisplayName("Полная дуга: нелепая среда → осознание кита → потеря китовости → стабилизация")
    void fullNarrativeArc() {
        WhaleSubject w = new WhaleSubject();
        w.enterUnnaturalCircumstances();
        w.realizeCetaceanIdentity();
        w.registerLossOfWhalehood();
        w.completePostWhaleAdjustment();
        assertEquals(WhaleNarrativeState.STABILIZED_AFTER_TRANSFORMATION, w.getNarrativeState());
        assertEquals(TemporalResource.REMAINING, w.getTimeForWhaleSelfIntegration());
    }
}
