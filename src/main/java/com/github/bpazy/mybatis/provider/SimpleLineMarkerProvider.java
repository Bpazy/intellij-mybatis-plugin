package com.github.bpazy.mybatis.provider;

import com.intellij.codeHighlighting.Pass;
import com.intellij.codeInsight.daemon.GutterIconNavigationHandler;
import com.intellij.codeInsight.daemon.LineMarkerInfo;
import com.intellij.openapi.editor.markup.GutterIconRenderer;
import com.intellij.pom.Navigatable;
import com.intellij.psi.PsiElement;
import com.intellij.util.Function;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.swing.*;

/**
 * @author yanglin
 */
public abstract class SimpleLineMarkerProvider<F extends PsiElement, T> extends MarkerProviderAdaptor {

    @Override
    public void collectSlowLineMarkers(@NotNull List<PsiElement> elements, @NotNull Collection<LineMarkerInfo> result) {
    }

    @SuppressWarnings("unchecked")
    @Nullable
    @Override
    public LineMarkerInfo getLineMarkerInfo(@NotNull PsiElement element) {
        if (!isTheElement(element)) return null;

        Optional<T> processResult = apply((F) element);
        return processResult.map(t -> new LineMarkerInfo<>(
                (F) element,
                element.getTextRange(),
                getIcon(),
                Pass.UPDATE_ALL,
                getTooltipProvider(t),
                getNavigationHandler(t),
                GutterIconRenderer.Alignment.CENTER
        )).orElse(null);
    }

    private Function<F, String> getTooltipProvider(final T target) {
        return from -> getTooltip(from, target);
    }

    private GutterIconNavigationHandler<F> getNavigationHandler(final T target) {
        return (e, from) -> getNavigatable(from, target).navigate(true);
    }

    public abstract boolean isTheElement(@NotNull PsiElement element);

    @NotNull
    public abstract Optional<T> apply(@NotNull F from);

    @NotNull
    public abstract Navigatable getNavigatable(@NotNull F from, @NotNull T target);

    @NotNull
    public abstract String getTooltip(@NotNull F from, @NotNull T target);

    @NotNull
    public abstract Icon getIcon();
}
