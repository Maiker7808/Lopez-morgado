package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "agent_logs")
data class AgentLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val timestamp: Long = System.currentTimeMillis(),
    val agentName: String,
    val action: String,
    val status: String, // "SUCCESS", "PENDING", "ALERT", "ERROR"
    val details: String
)

@Entity(tableName = "portfolio_items")
data class PortfolioItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val symbol: String,
    val name: String,
    val type: String, // "CRYPTO", "STOCK", "FOREX"
    val quantity: Double,
    val buyPrice: Double,
    val currentPrice: Double,
    val volatilityAlert: Boolean = false
)

@Entity(tableName = "scheduled_tasks")
data class ScheduledTaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val module: String, // "FINANCE", "CODING", "SOCIAL", "ORCHESTRATOR"
    val scheduleTime: String,
    val enabled: Boolean = true
)
