package com.github.bpazy.mybatis.alias;

import com.google.common.collect.Collections2;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReferenceBase;
import com.intellij.psi.xml.XmlAttributeValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author yanglin
 */
public class AliasClassReference extends PsiReferenceBase<XmlAttributeValue> {

    private Function<AliasDesc, String> function = AliasDesc::getAlias;

    public AliasClassReference(@NotNull XmlAttributeValue element) {
        super(element, true);
    }

    @Nullable
    @Override
    public PsiElement resolve() {
        XmlAttributeValue attributeValue = getElement();
        return AliasFacade.getInstance(attributeValue.getProject()).findPsiClass(attributeValue, attributeValue.getValue()).orElse(null);
    }

    @NotNull
    @Override
    public Object[] getVariants() {
        AliasFacade aliasFacade = AliasFacade.getInstance(getElement().getProject());
        return aliasFacade.getAliasDescs(getElement()).stream().map(function).toArray(String[]::new);
    }

}
