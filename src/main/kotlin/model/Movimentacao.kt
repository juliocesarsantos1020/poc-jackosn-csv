package model

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal
import java.time.LocalDate

data class Movimentacao(
    
    @JsonProperty("CNPJ_FUNDO")
    val cnpj: String,
    
    @JsonProperty("DT_COMPTC")
    val data: LocalDate,
    
    @JsonProperty("VL_TOTAL")
    val valorTotal: BigDecimal? = BigDecimal.ZERO,
    
    @JsonProperty("VL_QUOTA")
    val valorQuota: BigDecimal,
    
    @JsonProperty("VL_PATRIM_LIQ")
    val valorPatrimonio: BigDecimal? = BigDecimal.ZERO,
    
    @JsonProperty("CAPTC_DIA")
    val capctacaoDia: BigDecimal? = BigDecimal.ZERO,
    
    @JsonProperty("RESG_DIA")
    val resgateDia: BigDecimal? = BigDecimal.ZERO,
    
    @JsonProperty("NR_COTST")
    val nr: BigDecimal? = BigDecimal.ZERO
)
