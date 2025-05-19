// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

/**
 * @title Satellite Alert and Action System
 * @dev Combines alert submission, confirmation, and validator actions in one contract
 * Designed for Proof-of-Authority (PoA) blockchains with satellite nodes
 */
contract SatelliteSystem {
    // =============================================
    // Structs and Enums
    // =============================================

    struct Alert {
        address sender;
        string alertType;
        uint256 latitude;
        uint256 longitude;
        uint256 timestamp;
        uint256 confirmations;
        bool validated;
        address[] confirmers;
    }

    enum SatelliteAction {
        SWITCH_ORBIT,
        SWITCH_SENSOR
    }

    // =============================================
    // State Variables
    // =============================================

    mapping(address => bool) public validators;
    uint256 public requiredConfirmations;

    mapping(bytes32 => Alert) public alerts;
    bytes32[] public alertIds;
    mapping(address => bool) public registeredSatellites;

    // =============================================
    // Events
    // =============================================

    event AlertSubmitted(
        bytes32 indexed alertId,
        address indexed sender,
        string alertType,
        uint256 latitude,
        uint256 longitude
    );

    event AlertConfirmed(
        bytes32 indexed alertId,
        address indexed confirmer
    );

    event AlertValidated(
        bytes32 indexed alertId
    );

    event ActionTriggered(
        address indexed satellite,
        SatelliteAction action,
        bytes32 indexed alertId,
        uint256 timestamp
    );

    // =============================================
    // Modifiers
    // =============================================

    modifier onlyValidator() {
        require(validators[msg.sender], "Only validators can call");
        _;
    }

    modifier onlyRegisteredSatellite() {
        require(registeredSatellites[msg.sender], "Only registered satellites");
        _;
    }

    // =============================================
    // Constructor
    // =============================================

    constructor(
        uint256 _requiredConfirmations,
        address[] memory _initialSatellites,
        address[] memory _validators
    ) {
        requiredConfirmations = _requiredConfirmations;

        for (uint i = 0; i < _initialSatellites.length; i++) {
            registeredSatellites[_initialSatellites[i]] = true;
        }

        for (uint i = 0; i < _validators.length; i++) {
            validators[_validators[i]] = true;
        }
    }

    // =============================================
    // Core Functions
    // =============================================

    function submitAlert(
        string memory _alertType,
        uint256 _latitude,
        uint256 _longitude
    ) external onlyRegisteredSatellite returns (bytes32) {
        bytes32 alertId = keccak256(
            abi.encodePacked(
                msg.sender,
                _alertType,
                _latitude,
                _longitude,
                block.timestamp
            )
        );

        alerts[alertId] = Alert({
            sender: msg.sender,
            alertType: _alertType,
            latitude: _latitude,
            longitude: _longitude,
            timestamp: block.timestamp,
            confirmations: 0,
            validated: false,
            confirmers: new address[](0)
        });

        alertIds.push(alertId);
        emit AlertSubmitted(alertId, msg.sender, _alertType, _latitude, _longitude);
        return alertId;
    }

    function confirmAlert(bytes32 _alertId) external onlyRegisteredSatellite {
        require(!alerts[_alertId].validated, "Alert already validated");
        require(alerts[_alertId].sender != msg.sender, "Cannot confirm own alert");

        for (uint i = 0; i < alerts[_alertId].confirmers.length; i++) {
            require(alerts[_alertId].confirmers[i] != msg.sender, "Already confirmed");
        }

        alerts[_alertId].confirmations++;
        alerts[_alertId].confirmers.push(msg.sender);
        emit AlertConfirmed(_alertId, msg.sender);

        if (alerts[_alertId].confirmations >= requiredConfirmations) {
            alerts[_alertId].validated = true;
            emit AlertValidated(_alertId);
        }
    }

    function triggerAction(
        address _satellite,
        SatelliteAction _action,
        bytes32 _alertId
    ) external onlyValidator {
        require(alerts[_alertId].validated, "Alert not validated");
        require(registeredSatellites[_satellite], "Invalid satellite");

        emit ActionTriggered(_satellite, _action, _alertId, block.timestamp);
    }

    // =============================================
    // Management Functions
    // =============================================

    function registerSatellite(address _satellite) external onlyValidator {
        registeredSatellites[_satellite] = true;
    }

    function updateRequiredConfirmations(uint256 _newValue) external onlyValidator {
        requiredConfirmations = _newValue;
    }

    function addValidator(address _validator) external onlyValidator {
        validators[_validator] = true;
    }

    function removeValidator(address _validator) external onlyValidator {
        validators[_validator] = false;
    }

    // =============================================
    // View Functions
    // =============================================

    function getAlertConfirmers(bytes32 _alertId) external view returns (address[] memory) {
        return alerts[_alertId].confirmers;
    }

    function getAlertCount() external view returns (uint256) {
        return alertIds.length;
    }
}
