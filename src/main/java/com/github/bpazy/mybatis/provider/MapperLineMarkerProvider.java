package com.github.bpazy.mybatis.provider;

import com.github.bpazy.mybatis.dom.model.IdDomElement;
import com.github.bpazy.mybatis.service.JavaService;
import com.github.bpazy.mybatis.util.Icons;
import com.github.bpazy.mybatis.util.JavaUtils;
import com.google.common.collect.Collections2;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider;
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder;
import com.intellij.openapi.editor.markup.GutterIconRenderer;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNameIdentifierOwner;
import com.intellij.psi.xml.XmlTag;
import com.intellij.util.CommonProcessors;
import com.intellij.util.xml.DomElement;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author yanglin
 */
public class MapperLineMarkerProvider extends RelatedItemLineMarkerProvider {

    private static final Function<DomElement, XmlTag> FUN = DomElement::getXmlTag;

    @Override
    protected void collectNavigationMarkers(@NotNull PsiElement element, @NotNull Collection<? super RelatedItemLineMarkerInfo> result) {
        if (element instanceof PsiNameIdentifierOwner && JavaUtils.isElementWithinInterface(element)) {
            CommonProcessors.CollectProcessor<IdDomElement> processor = new CommonProcessors.CollectProcessor<>();
            JavaService.getInstance(element.getProject()).process(element, processor);
            Collection<IdDomElement> results = processor.getResults();
            if (!results.isEmpty()) {
                NavigationGutterIconBuilder<PsiElement> builder =
                        NavigationGutterIconBuilder.create(Icons.MAPPER_LINE_MARKER_ICON)
                                .setAlignment(GutterIconRenderer.Alignment.CENTER)
                                .setTargets(results.stream().map(FUN).collect(Collectors.toList()))
                                .setTooltipTitle("Navigation to target in mapper xml");
                result.add(builder.createLineMarkerInfo(((PsiNameIdentifierOwner) element).getNameIdentifier()));
            }
        }
    }

}
