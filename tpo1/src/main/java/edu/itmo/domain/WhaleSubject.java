package edu.itmo.domain;

public final class WhaleSubject {

    private BioSocialPredicament predicament = BioSocialPredicament.ORDINARY;
    private TemporalResource timeForWhaleSelfIntegration = TemporalResource.REMAINING;
    private WhaleNarrativeState narrativeState = WhaleNarrativeState.DISORIENTED_WHALE;

    public BioSocialPredicament getPredicament() {
        return predicament;
    }

    public TemporalResource getTimeForWhaleSelfIntegration() {
        return timeForWhaleSelfIntegration;
    }

    public WhaleNarrativeState getNarrativeState() {
        return narrativeState;
    }

    public void enterUnnaturalCircumstances() {
        predicament = BioSocialPredicament.UNNATURAL_FOR_WHALE_BODY;
    }

    //осознание только если есть время и нелепая среда
    public void realizeCetaceanIdentity() {
        if (predicament != BioSocialPredicament.UNNATURAL_FOR_WHALE_BODY) {
            return;
        }
        if (timeForWhaleSelfIntegration == TemporalResource.EXHAUSTED) {
            return;
        }
        if (narrativeState != WhaleNarrativeState.DISORIENTED_WHALE) {
            return;
        }
        narrativeState = WhaleNarrativeState.WHALE_IDENTITY_INTEGRATING;
    }

    // Исчерпание времени на привыкание к образу кита
    public void elapseTimeForWhaleIntegration() {
        timeForWhaleSelfIntegration = TemporalResource.EXHAUSTED;
        if (narrativeState == WhaleNarrativeState.WHALE_IDENTITY_INTEGRATING) {
            narrativeState = WhaleNarrativeState.ADJUSTING_AFTER_LOSING_WHALEHOOD;
        }
    }

    //перестаем быть китом
    public void registerLossOfWhalehood() {
        if (narrativeState == WhaleNarrativeState.STABILIZED_AFTER_TRANSFORMATION) {
            return;
        }
        if (narrativeState == WhaleNarrativeState.ADJUSTING_AFTER_LOSING_WHALEHOOD) {
            return;
        }
        narrativeState = WhaleNarrativeState.ADJUSTING_AFTER_LOSING_WHALEHOOD;
    }

    //завершение привыкания быть не китом
    public void completePostWhaleAdjustment() {
        if (narrativeState == WhaleNarrativeState.ADJUSTING_AFTER_LOSING_WHALEHOOD) {
            narrativeState = WhaleNarrativeState.STABILIZED_AFTER_TRANSFORMATION;
        }
    }
}
